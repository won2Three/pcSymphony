package domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "community")
public class CommunityEntity {

    //글 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id", nullable = false)
    private String communityId;
    //작성자 정보(외래키), 글작성자
    //여러개의 글이 한명의 작성자에 의해 작성
    @ManyToOne(fetch = FetchType.LAZY)//다대일 관게
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    MemberEntity memberEntity;
    //글 제목
    @Column(name = "community_title", nullable = false)
    private String communityTitle;
    //컨텐츠
    @Column(name = "community_content", nullable = false)
    private String communityContent;
    //조회수
    @Column(name = "community_view")
    private String communityView;
    //등록일
    @CreatedDate
    @Column(name = "community_date")
    private String communityDate;
}
