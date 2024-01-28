package blog.domain;

import blog.PostApplication;
<<<<<<< HEAD
import blog.domain.PostCreated;
import blog.domain.PostDeleted;
import blog.domain.PostEdited;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
=======
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.*;

import org.springframework.transaction.annotation.Transactional;

>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
import lombok.Data;

@Entity
@Table(name = "Post_table")
@Data
<<<<<<< HEAD
//<<< DDD / Aggregate Root
=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

<<<<<<< HEAD
=======
    private Long userId;

>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
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
<<<<<<< HEAD
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
=======
                PostRepository.class);
        return postRepository;
    }

    // 진승찬 : 게시글에 댓글이 달렸을 때, 게시글의 댓글리스트 필드에 댓글 추가하는 메서드
    @Transactional
    public static void commentCreateOnPost(CommentCreated commentCreated) {
        repository().findById(commentCreated.getPostId()).ifPresent(post -> { 

            List<Long> newCommentList = new ArrayList<>(post.getCommentList());
                                                                                
            newCommentList.add(commentCreated.getId()); 
            post.setCommentList(newCommentList); 

            repository().save(post);

        });
    }

    // 진승찬 : 게시글에 댓글이 삭제되었을 때, 게시글의 댓글리스트 필드에 댓글을 삭제하는 메서드
    @Transactional
    public static void commentDeleteOnPost(CommentDeleted commentDeleted) {

        repository().findById(commentDeleted.getId()).ifPresent(post -> {

            List<Long> newCommentList = new ArrayList<>(post.getCommentList());
            newCommentList.remove(commentDeleted.getId());
            post.setCommentList(newCommentList);
            repository().save(post);

        });
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
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
