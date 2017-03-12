package com.drivingschool.controller;

import com.drivingschool.model.Document;
import com.drivingschool.model.Picture;
import com.drivingschool.model.User;
import com.drivingschool.service.IDocumentService;
import com.drivingschool.service.IPictureService;
import com.drivingschool.service.IUserService;
import com.drivingschool.utils.PermissionUtils;
import com.drivingschool.utils.RolesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";
    private final IUserService userService;
    private final IDocumentService documentService;
    private final IPictureService pictureService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserController(IUserService userService, IDocumentService documentService, IPictureService pictureService) {
        this.userService = userService;
        this.documentService = documentService;
        this.pictureService = pictureService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAll() {

        List<User> usersResult = userService.findAll();
        // TODO : check later if there is any user filter by school
        if (usersResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usersResult, HttpStatus.OK);
    }

    @PreAuthorize(PermissionUtils.HAS_ANY_ROLE)
    @RequestMapping(value = "/{role}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listByRole(@PathVariable("role") String role) {

        List<User> usersResult = userService.findByRole(role);
        // TODO : check later if there is any user filter by school
        if (usersResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usersResult, HttpStatus.OK);
    }

    @PreAuthorize(PermissionUtils.HAS_ANY_ROLE)
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public ResponseEntity<User> getAuthenticatedUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.read(auth.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        // encode the password of the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        // get the current user connected
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            // check if the current user has only a user role
            if (authority.getAuthority().equals("ROLE_USER")) {
                // check if the user is updating its own details, otherwise, return http error
                if (!auth.getName().equals(user.getUsername())) {
                    return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
                }
            }
        }
        User old_user = userService.read(user.getUsername());
        // check if the modification sets a new picture, otherwise, keep the old one
        if (old_user.getPic() != null)
            user.setPic(old_user.getPic());
        // encode the new password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable String username) {

        User user = new User();
        user.setUsername(username);
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize(PermissionUtils.HAS_ANY_ROLE)
    @RequestMapping(value = "/documents/upload/")
    public void uploadDocument(@RequestParam("document") MultipartFile document, @RequestParam("username") String username) throws IOException {

        Document doc = new Document();
        User user = new User();
        user.setUsername(username);
        if (!document.isEmpty()) {
            doc.setUser(user);
            doc.setName(document.getOriginalFilename());
            doc.setData(document.getBytes());
        }
        documentService.create(doc);
    }

    @PreAuthorize(PermissionUtils.HAS_ANY_ROLE)
    @RequestMapping(value = "/pictures/upload/")
    public void uploadPicture(@RequestParam("picture") MultipartFile picture, @RequestParam("username") String username) throws IOException {

        Picture pic = new Picture();
        User user = new User();
        user.setUsername(username);
        if (!picture.isEmpty()) {
            pic.setUser(user);
            pic.setName(picture.getOriginalFilename());
            pic.setData(picture.getBytes());
        }
        pictureService.create(pic);
    }

    @PreAuthorize(PermissionUtils.HAS_ANY_ROLE)
    @RequestMapping(value = "/pic/upload/")
    public void uploadProfilePicture(@RequestParam("picture") MultipartFile picture, @RequestParam("username") String username) throws IOException {

        User user = userService.read(username);
        if (!picture.isEmpty()) {
            user.setPic(picture.getBytes());
        }
        userService.update(user);
    }

    @PreAuthorize(PermissionUtils.HAS_ANY_ROLE)
    @RequestMapping(value = "/pic/{username}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable String username) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        if (username.equals(name)) {
            User user = userService.read(username);
            if (user.getPic() != null) {
                return new ResponseEntity<>(user.getPic(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize(PermissionUtils.HAS_ANY_ROLE)
    @RequestMapping(value = "/picture/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deletePicture(@PathVariable String id) {

        // TODO : add security aspect to verify it is the picture's owner who is calling the service
        Picture picture = pictureService.read(Integer.parseInt(id));
        pictureService.delete(picture);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize(PermissionUtils.HAS_ANY_ROLE)
    @RequestMapping(value = "/document/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteDocument(@PathVariable String id) {

        // TODO : add security aspect to verify it is the picture's owner who is calling the service
        Document document = documentService.read(Integer.parseInt(id));
        documentService.delete(document);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostConstruct
    public void initData() {
        // init default superadmin
        initUser("admin", "admin", RolesUtils.ROLE_SUPERADMIN);
        // init default admin
        initUser("admin1", "admin", RolesUtils.ROLE_ADMIN);
        initUser("admin2", "admin", RolesUtils.ROLE_ADMIN);
        initUser("admin3", "admin", RolesUtils.ROLE_ADMIN);
        // init monitors
        initUser("monitor1", "monitor", RolesUtils.ROLE_INSTRUCTOR);
        initUser("monitor2", "monitor", RolesUtils.ROLE_INSTRUCTOR);
        initUser("monitor3", "monitor", RolesUtils.ROLE_INSTRUCTOR);
        // init users
        initUser("user1", "user", RolesUtils.ROLE_USER);

    }

    private void initUser(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        if (userService.read(username) == null) {
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userService.create(user);
        }
    }
}
