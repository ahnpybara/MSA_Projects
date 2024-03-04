package blog.domain;

import blog.CommentApplication;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.*;


import lombok.Data;

@Entity
@Table(name = "Comment_table")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    private Long postId;

    private String nickname;

    private Long userId;

    @PostPersist
    public void onPostPersist() {
        CommentCreated commentCreated = new CommentCreated(this);
        commentCreated.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        CommentDeleted commentDeleted = new CommentDeleted(this);
        commentDeleted.publishAfterCommit();
    }

    public static CommentRepository repository() {
        CommentRepository commentRepository = CommentApplication.applicationContext.getBean(
                CommentRepository.class);
        return commentRepository;
    }

    public void create(CommentCreated commentCreated){
        setContent(commentCreated.getContent());
        setPostId(commentCreated.getPostId());
    }

    // 안치윤 : 게시글 삭제시 해당 게시글에 달린 댓글들 또한 모두 삭제하기
    public static void deleteAllComments(PostDeleted postDeleted) {

        List<Comment> comments = repository().findAllByPostId(postDeleted.getId());

        if (!comments.isEmpty()) {
            repository().deleteAll(comments);

            AllCommentsDeleted allCommentsDeleted = new AllCommentsDeleted(comments);
            allCommentsDeleted.publishAfterCommit();
        }
    }

    // 안치윤 : 유저 정보가 업데이트 되었을 때, 댓글 정보도 업데이트하는 메서드.
    public static void updateUser(UserUpdated userUpdated) {
        try {
            // 특정 유저id를 가진 댓글 객체를 리스트로 가져옵니다
            List<Comment> comment_list = repository().findByUserId(userUpdated.getId());

            // 가져온 댓글 리스트가 null이면 예외 발생
            if (comment_list == null) {
                throw new NoSuchElementException("Can't find user with id " + userUpdated.getId());
            }

            // 댓글 객체의 닉네임 부분을 수정된 닉네임으로 변경하고 이를 DB에 저장합니다
            for (Comment new_comment : comment_list) {
                new_comment.setNickname(userUpdated.getNickname());
                repository().save(new_comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}