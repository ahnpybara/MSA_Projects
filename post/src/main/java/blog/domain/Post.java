package blog.domain;

import blog.PostApplication;
import blog.domain.PostCreated;
import blog.domain.PostDeleted;
import blog.domain.PostEdited;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Post_table")
@Data
//<<< DDD / Aggregate Root
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

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
            PostRepository.class
        );
        return postRepository;
    }

    //<<< Clean Arch / Port Method
    public static void commentCreateOnPost(CommentCreated commentCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        Post post = new Post();
        repository().save(post);

        */

        /** Example 2:  finding and process
        
        repository().findById(commentCreated.get???()).ifPresent(post->{
            
            post // do something
            repository().save(post);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void commentDeleteOnPost(CommentDeleted commentDeleted) {
        //implement business logic here:

        /** Example 1:  new item 
        Post post = new Post();
        repository().save(post);

        */

        /** Example 2:  finding and process
        
        repository().findById(commentDeleted.get???()).ifPresent(post->{
            
            post // do something
            repository().save(post);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateUser(UserUpdated userUpdated) {
        //implement business logic here:

        /** Example 1:  new item 
        Post post = new Post();
        repository().save(post);

        */

        /** Example 2:  finding and process
        
        repository().findById(userUpdated.get???()).ifPresent(post->{
            
            post // do something
            repository().save(post);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
