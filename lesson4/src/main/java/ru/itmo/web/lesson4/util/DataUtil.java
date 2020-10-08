package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.User;
import ru.itmo.web.lesson4.model.Post;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.GREEN),
            new User(6, "pashka", "Pavel Mavrin", Color.RED),
            new User(9, "geranazarov555", "Georgiy Nazarov", Color.BLUE),
            new User(11, "tourist", "Gennady Korotkevich", Color.BLUE),
            new User(228, "DK318", "Leonid Vasilev", Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Codeforces Round #510 (Div. 2)", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Commodi consequatur magni non sed? Autem cum error et eveniet laborum molestias non quaerat quam sit temporibus?", 1),
            new Post(3, "Trying to make good post", "Hello. It's mine first post", 11),
            new Post(2, "Very long post", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consectetur consequuntur, eaque fugiat illo in nam necessitatibus nisi rem tenetur. Ab animi aperiam beatae commodi, consequatur corporis culpa cumque cupiditate dignissimos dolor doloremque eius eligendi enim ex exercitationem, expedita facere illum ipsum libero maiores, minus necessitatibus nisi nostrum nulla obcaecati odio porro praesentium ratione rem repellat temporibus tenetur vero voluptate. Animi architecto, consectetur consequatur consequuntur, culpa cum, deleniti dolore eius eos eum explicabo hic in incidunt inventore ipsa ipsam ipsum magnam nam nemo nesciunt nobis omnis provident quibusdam quis reiciendis unde vel. Dolorum exercitationem illo in inventore recusandae vitae voluptas voluptatem.", 9),
            new Post(4, "My second post", "I really tired coding this homework", 11)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
