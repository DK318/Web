package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.RoleRepository;
import ru.itmo.wp.repository.TagRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    /** @noinspection FieldCanBeLocal, unused */
    private final RoleRepository roleRepository;

    private final TagRepository tagRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tagRepository = tagRepository;

        for (Role.Name name : Role.Name.values()) {
            if (roleRepository.countByName(name) == 0) {
                roleRepository.save(new Role(name));
            }
        }
    }

    public User register(UserCredentials userCredentials) {
        User user = new User();
        user.setLogin(userCredentials.getLogin());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), userCredentials.getLogin(), userCredentials.getPassword());
        return user;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public void writePost(User user, PostForm postForm) {
        String[] tagArray = null;

        String tags = postForm.getTags();
        if (tags != null && !tags.isEmpty()) {
            tagArray = tags.split(" ");
        }

        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setText(postForm.getText());

        if (tagArray != null) {
            Set<Tag> tagSet = new HashSet<>();
            for (String tag : tagArray) {
                if (tagRepository.countByName(tag) == 0) {
                    tagRepository.save(new Tag(tag));
                }

                Tag tagEntity = tagRepository.findByName(tag);
                tagSet.add(tagEntity);
            }
            post.setTags(tagSet);
        }

        user.addPost(post);
        userRepository.save(user);
    }
}
