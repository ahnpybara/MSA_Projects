package blog.domain;

import blog.PostApplication;
import blog.domain.PostCreated;
import blog.domain.PostDeleted;
import blog.domain.PostEdited;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Post_table")
@Data
// <<< DDD / Aggregate Root
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Long userId;

    @ElementCollection
    private List<Long> commentList;

    private String nickname;

    @PostPersist
    public void onPostPersist() {
        PostCreated postCreated = new PostCreated(this);
        postCreated.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        PostEdited postEdited = new PostEdited(this);
        postEdited.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        PostDeleted postDeleted = new PostDeleted(this);
        postDeleted.publishAfterCommit();
    }

    public static PostRepository repository() {
        PostRepository postRepository = PostApplication.applicationContext.getBean(
                PostRepository.class);
        return postRepository;
    }

    // <<< Clean Arch / Port Method
    public static void commentCreateOnPost(CommentCreated commentCreated) {
        // implement business logic here:

        /**
         * Example 1: new item
         * Post post = new Post();
         * repository().save(post);
         * 
         */

        /**
         * Example 2: finding and process
         * 
         * repository().findById(1).ifPresent(post->{
         * 
         * post // do something
         * repository().save(post);
         * 
         * 
         * });
         */

    }

    // >>> Clean Arch / Port Method
    // <<< Clean Arch / Port Method
    public static void commentDeleteOnPost(CommentDeleted commentDeleted) {
        // implement business logic here:

        /**
         * Example 1: new item
         * Post post = new Post();
         * repository().save(post);
         * 
         */

        /**
         * Example 2: finding and process
         * 
         * repository().findById(commentDeleted.get???()).ifPresent(post->{
         * 
         * post // do something
         * repository().save(post);
         * 
         * 
         * });
         */

    }

    // 안치윤 : 유저 정보가 업데이트 되었을 때, 게시글 정보도 업데이트하는 메서드
    public static void updateUser(UserUpdated userUpdated) {
        try {
            // 특정 유저id를 가진 게시글 객체를 리스트로 가져옵니다
            List<Post> post_list = repository().findByUserId(userUpdated.getId());

            // 가져온 게시글 리스트가 null이면 예외 발생
            if (post_list == null) {
                throw new NoSuchElementException("Can't find user with id " + userUpdated.getId());
            }

            // 게시글 객체의 닉네임 부분을 수정된 닉네임으로 변경하고 이를 DB에 저장합니다 
            for (Post new_post : post_list) {
                new_post.setNickname(userUpdated.getNickname());
                repository().save(new_post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
