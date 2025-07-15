package com.asia.forum.boardgames.dao.impl;

import com.asia.forum.boardgames.dao.IPostDAO;
import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PostsRepository implements IPostDAO {
    private final HashMap<Integer, List<Post>> posts = new HashMap<>();
    private final Map<Integer, Integer> lastPostIdPerTopic = new HashMap<>();





    public PostsRepository() {
        this.posts.put(1, new ArrayList<>(List.of(
                new Post(1, 1, "admin",
                        """
                📜 Regulamin Forum Gier Planszowych
                
                1. Postanowienia ogólne
                1.1. Forum służy do wymiany opinii, wiedzy i doświadczeń związanych z grami planszowymi.
                1.2. Korzystanie z forum oznacza akceptację niniejszego regulaminu.
                1.3. Administratorzy i moderatorzy mają prawo egzekwować regulamin i podejmować działania w celu utrzymania porządku.
                
                2. Zasady korzystania z forum
                2.1. Użytkownik zobowiązany jest do kulturalnego i rzeczowego wypowiadania się.
                2.2. Zabrania się:
                - obrażania innych użytkowników, używania wulgaryzmów i mowy nienawiści,
                - spamowania, floodowania i reklamowania innych serwisów bez zgody administracji,
                - publikowania treści niezgodnych z prawem lub naruszających prawa autorskie.
                2.3. Wypowiedzi powinny być zgodne z tematem działu i wątku.
                2.4. Zakładając nowy temat, należy upewnić się, że nie istnieje już podobny wątek.
                
                3. Konta użytkowników
                3.1. Każdy użytkownik może posiadać tylko jedno konto.
                3.2. Nick i avatar nie mogą zawierać treści obraźliwych, wulgarnych ani reklamowych.
                3.3. Administratorzy mogą zablokować konto za rażące naruszenia regulaminu.
                
                4. Administracja i moderatorzy
                4.1. Administratorzy i moderatorzy mają prawo usuwać posty, zamykać wątki i udzielać ostrzeżeń.
                4.2. W przypadku sporu użytkownik może odwołać się od decyzji moderatora do administratora.
                
                5. Postanowienia końcowe
                5.1. Regulamin może być aktualizowany – o zmianach użytkownicy będą informowani na forum.
                5.2. Nieznajomość regulaminu nie zwalnia z obowiązku jego przestrzegania.
                5.3. W sprawach nieuregulowanych regulaminem decyzję podejmuje administracja.
                
                Proszę wpisać w odpowiedzi 'Akceptuję regulamin'.
                """, LocalDateTime.of(2025, 6, 29, 14, 30)
                ))));

        this.posts.put(2, new ArrayList<>(List.of(
                new Post(1, 2,
                "asia123", "Niedawno wyszło nowe DLC do Zapomnianych Mórz, czy " +
                "ktoś już może grał? Jak wrażenia?",
                        LocalDateTime.of(2025, 6, 30, 18, 32)),
                new Post(2, 2, "janusz1", "Grałem. Według mnie " +
                        "najlepszy scenariusz zaraz po Sercu Wiedźmy",
                        LocalDateTime.of(2025, 6, 30, 21, 45)))));

        this.posts.put(3, new ArrayList<>(List.of(
                new Post(1,3, "asia123", "Do osób, które grały w Ark Nova, jak Wasze " +
                        "wrażenia z rozgrywki?",
                        LocalDateTime.of(2025, 7, 1, 12, 30)),
                new Post(2, 3, "janusz1", "Niestey u mnie " +
                        "Ark Nova nadal leży na półce wstydu...",
                        LocalDateTime.of(2025, 7, 1, 13, 30)),
                new Post(3, 3, "ModeratorPierwszy", "Gra jest podobna " +
                        "do Terraformacji Marsa ale według mnie rozgrywka jest dużo ciekawsza.",
                        LocalDateTime.of(2025, 7, 2, 14, 13)),
                new Post(4, 3, "asia123", "A na czym polega podobnieństwo " +
                        "do TM?", LocalDateTime.of(2025, 7, 3, 7,54)))));

        this.posts.put(4, new ArrayList<>(List.of(
                new Post(1, 4, "ModeratorPierwszy",  """
                    🏆 Konkurs na Najciekawszą Recenzję Gry Planszowej!
                    
                    Lubisz dzielić się opinią o grach planszowych? Masz lekkie pióro i ciekawe spojrzenie na tytuły, w które grasz? Ten konkurs jest dla Ciebie!
                    
                    📝 Zadanie konkursowe:
                    Napisz autorską recenzję dowolnej gry planszowej i opublikuj ją w dziale „Recenzje” na forum.
                    
                    🎯 Co oceniamy?
                    - Oryginalność i styl recenzji
                    - Jasność przekazu i czytelność
                    - Merytoryczność (opis rozgrywki, mechanik, zalet i wad)
                    - Własne przemyślenia i subiektywna ocena
                    
                    📅 Termin nadsyłania recenzji: do 31 lipca 2025
                    
                    🎁 Nagrody:
                    1. miejsce – bon 200 zł do sklepu z grami
                    2. miejsce – gra planszowa niespodzianka
                    3. miejsce – forumowy medal i wyróżnienie
                    
                    👥 Wyniki:
                    Zwycięzców ogłosi administracja do 5 sierpnia 2025. Najlepsze recenzje zostaną wyróżnione na stronie głównej forum.
                    
                    📌 Zasady:
                    - Jedna recenzja na użytkownika
                    - Tylko prace autorskie (niepublikowane wcześniej)
                    - Recenzja powinna zawierać min. 200 znaków
                    
                    Powodzenia i do piór (klawiatur)! ✍️
                    """, LocalDateTime.of(2025, 7, 1, 9, 0) ))));
        this.lastPostIdPerTopic.put(1, 1);
        this.lastPostIdPerTopic.put(2, 2);
        this.lastPostIdPerTopic.put(3, 4);
        this.lastPostIdPerTopic.put(4, 1);
    }



    @Override
    public void addPost(int topicId, Post post) {
        if (!posts.containsKey(topicId)){
            posts.put(topicId, new ArrayList<Post>());
        }
        posts.get(topicId).add(post);

    }

    @Override
    public Post getPostById(int id) {
        return null;
    }

    @Override
    public List<Post> getAllPostsForTopicId(int topicId) {
        return posts.get(topicId);
    }

    @Override
    public void persistPost(Post post) {
        int topicId = post.getTopicId();

        // Jeśli temat nie istnieje, inicjalizujemy listę
        if (!posts.containsKey(topicId)){
            posts.put(topicId, new ArrayList<>());
            lastPostIdPerTopic.put(topicId, 0);
        }

        List<Post> postList = posts.get(topicId);

        // Pobieramy ostatnie ID z mapy, jeśli jest, jeśli nie to 0
        int lastId = lastPostIdPerTopic.getOrDefault(topicId,0);
        int newId = lastId + 1;

        Post postToPersist = new Post(
                newId,
                topicId,
                post.getAuthor(),
                post.getContent(),
                post.getDate()
        );

        // Dodajemy nowy post do listy i aktualizujemy ostatnie ID
        postList.add(postToPersist);
        lastPostIdPerTopic.put(topicId, newId);


    }

    @Override
    public Post getLatestPostForTopicId(int topicId) {
        List<Post> postList = posts.get(topicId);
        if (postList != null && !postList.isEmpty()) {
            return postList.get(postList.size() - 1);
        }
        return null;
    }

    @Override
    public String getLastPostAuthor(int topicId){
        Post latestPost = getLatestPostForTopicId(topicId);
        if(latestPost != null){
            return latestPost.getAuthor();
        }
        return null;
    }

    @Override
    public HashMap<Integer, List<Post>> getAllPostsForTopics(List<Topic> topics) {
        HashMap<Integer, List<Post>> result = new HashMap<>();
        for (Topic topic : topics) {
            List<Post> postsForTopic = getAllPostsForTopicId(topic.getId());
            if (postsForTopic != null && !postsForTopic.isEmpty()) {
                result.put(topic.getId(), postsForTopic);
            }
        }
        return result;
    }

    public LocalDateTime getLastPostDate(int topicId){
        Post latestPost = getLatestPostForTopicId(topicId);
        if(latestPost != null) {
            return latestPost.getDate();
        }
        return null;
    }

    @Override
    public void updatePost(int topicId, int postId, String newContent) {
        List<Post> topicPosts = posts.get(topicId);
        if (topicPosts != null) {
            for (Post post : topicPosts) {
                if (post.getId() == postId) {
                    post.setContent(newContent);
                    break;
                }
            }
        }
    }

    @Override
    public void deletePost(int topicId, int postId) {
        List<Post> topicPosts = posts.get(topicId);
        if (topicPosts != null) {
            for (int i = 0; i < topicPosts.size(); i++) {
                if (topicPosts.get(i).getId() == postId) {
                    topicPosts.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    public Post getPost(int topicId, int postId) {
        List<Post> topicPosts = posts.get(topicId);
        if (topicPosts != null) {
            for (Post post : topicPosts) {
                if (post.getId() == postId) {
                    return post;
                }
            }
        }
        return null;
    }

}
