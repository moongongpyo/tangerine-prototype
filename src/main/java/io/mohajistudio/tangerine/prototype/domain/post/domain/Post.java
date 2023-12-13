package io.mohajistudio.tangerine.prototype.domain.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.mohajistudio.tangerine.prototype.domain.member.domain.Member;
import io.mohajistudio.tangerine.prototype.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate visitedAt;

    @Column(nullable = false)
    private int commentCnt = 0;

    @Column(nullable = false)
    private int favoriteCnt = 0;

    private short blockCnt = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

//    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
//    private TrendingPost trendingPost;

    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<ScrapPost> scrapPosts;

    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<FavoritePost> favoritePosts;

    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<TextBlock> textBlocks;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<PlaceBlock> placeBlocks;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setPlaceBlocks(List<PlaceBlock> placeBlocks) {
        this.placeBlocks = placeBlocks;
        blockCnt += (short) placeBlocks.size();
        placeBlocks.forEach(placeBlock -> placeBlock.setPost(this));
    }

    public void setTextBlocks(List<TextBlock> textBlocks) {
        this.textBlocks = textBlocks;
        blockCnt += (short) textBlocks.size();
        textBlocks.forEach(textBlock -> textBlock.setPost(this));
    }
}
