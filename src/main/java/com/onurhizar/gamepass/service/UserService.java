package com.onurhizar.gamepass.service;

import com.onurhizar.gamepass.exception.EntityNotFoundException;
import com.onurhizar.gamepass.exception.UnacceptableRequestException;
import com.onurhizar.gamepass.model.entity.*;
import com.onurhizar.gamepass.model.enums.UserRole;
import com.onurhizar.gamepass.model.request.CreateUserRequest;
import com.onurhizar.gamepass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final GameService gameService;
    private final CategoryService categoryService;
    private final SubscriptionService subscriptionService;
    private final ContractRecordService contractRecordService;
    private final InvoiceService invoiceService;
    private final PasswordEncoder passwordEncoder;


    public User addUser(CreateUserRequest request){
        User foundUser = repository.findByEmail(request.getEmail()); // check if email exists
        if (foundUser != null) throw new UnacceptableRequestException("email exists");


        User newUser = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.GUEST) // default
                .build();
        return repository.save(newUser);
    }

    public void deleteUser(String userId){
        User user = findById(userId);
        repository.delete(user);
    }


    public User updateUser(String id, CreateUserRequest userDto) {
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        // TODO : a better approach? too much repetition
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getSurname() != null) user.setSurname(userDto.getSurname());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        }
        return repository.save(user);
    }

    public List<User> listUsers(){
        return repository.findAll();
    }


    public User findById(String id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    @Transactional
    public ContractRecord subscribe(String userId, String subscriptionId){
        User user = findById(userId);
        Subscription newSubscription = subscriptionService.findById(subscriptionId);

        // check if user is verified
        if(!user.isVerified()) throw new UnacceptableRequestException("only verified users can subscribe");

        // check if user has already a subscription (allow only upgrading)
        ContractRecord contractRecord = user.getContractRecord();

        if (contractRecord != null){
            if (contractRecord.getMonthlyFee() >= newSubscription.getMonthlyFee())
                throw new UnacceptableRequestException("you can only upgrade your subscription");

            findInvoicesInCurrentMonthAndUpdateTheirFees(contractRecord, newSubscription);
        }

        // when a guest user buys a subscription, assign a member role
        if (user.getRole() == UserRole.GUEST) user.setRole(UserRole.MEMBER);

        if (contractRecord == null) return contractRecordService.addContract(user, newSubscription);
        else return contractRecordService.updateContract(contractRecord, newSubscription);
    }

    // Interests : Follow Categories and Favorite Games
    public User favoriteGame(String userId, String gameId){
        return addOrRemoveGameFromUserFavoriteGames(userId, gameId, true);
    }

    public User unfavoriteGame(String userId, String gameId){
        return addOrRemoveGameFromUserFavoriteGames(userId, gameId, false);
    }

    public User followCategory(String userId, String categoryId){
        return followHelper(userId, categoryId, true);
    }

    public User unfollowCategory(String userId, String categoryId){
        return followHelper(userId, categoryId, false);
    }

    private void findInvoicesInCurrentMonthAndUpdateTheirFees(ContractRecord contractRecord, Subscription subscription){
        // find invoices in current month to update their fees
        List<Invoice> invoicesInThisMonth = invoiceService.findInvoicesOfContractRecordInCurrentMonth(
                contractRecord.getId());
        for (Invoice invoice : invoicesInThisMonth){
            invoice.setFee(subscription.getMonthlyFee());
        }
    }

    /**
     * Helper method to avoid duplicate codes.
     * boolean isAddition field checks if it is a favorite or unfavorite method
     */
    private User addOrRemoveGameFromUserFavoriteGames(String userId, String gameId, boolean isAddition){
        User user = findById(userId);
        Game game = gameService.findGameById(gameId);
        List<Game> games = user.getFavoriteGames();

        if (isAddition && !games.contains(game)) games.add(game);
        else if (!isAddition && games.contains(game)) games.remove(game);
        else return user; // there is no change, don't save anything just return the user

        repository.save(user);
        return user;
    }

    /**
     * Helper method to avoid duplicate codes. <br>
     * (TODO still needs a refactor of duplicate codes with addOrRemoveGameFromUserFavoriteGames method) <br>
     * To refactor, maybe use ICrudService interface? and give the method of class and its service. <br>
     * boolean isFollow field checks if it is a follow or unfollow request
     */
    private User followHelper(String userId, String categoryId, boolean isFollow){
        User user = findById(userId);
        Category category = categoryService.findCategoryById(categoryId);

        List<Category> categories = user.getFollowedCategories();

        if (isFollow && !categories.contains(category)) categories.add(category);
        else if (!isFollow && categories.contains(category)) categories.remove(category);
        else return user; // there is no change, don't save anything just return the user

        repository.save(user);
        return user;
    }

    /** Disable downgrade logic for ADMIN users. Only regular members downgrade when unpaid invoices */
    public void downgradeNonAdminUserRoleToGuest(String userId){
        User user = findById(userId);
        if (user == null) throw new EntityNotFoundException();

        if (user.getRole() != UserRole.ADMIN) {
            user.setRole(UserRole.GUEST);
            repository.save(user);
        }
    }

}
