package comDimka.example.homework_04.controller;

import comDimka.example.homework_04.models.User;
import comDimka.example.homework_04.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/getAll")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    //http://localhost:8080/users/getByName?name=John%20Doe
    @GetMapping("/getByName")
    public List<User> getByName(@RequestParam String name) {
        return userRepository.findUserByName(name);
    }

    @GetMapping("/getById/{id}")
    public User getById(@PathVariable long id) {
        return userRepository.findUserById(id);
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    //    public void updateUserInfo(Long id, String name, String email) {
//        userRepository.updateUserInfo(name, email, id);
//    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userRepository.findUserById(id);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        userRepository.save(existingUser);

        return ResponseEntity.ok("User information updated successfully");
    }

    //    public void deleteUserById(Long id) {
//        userRepository.deleteUserById(id);
//    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User existingUser = userRepository.findUserById(id);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(existingUser);

        return ResponseEntity.ok("User deleted successfully");
    }
}
