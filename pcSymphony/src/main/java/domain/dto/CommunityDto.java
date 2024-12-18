package domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommunityDto {

    private int communityId;
    private String communityTitle;
    private String communityContent;
    private String communityView;
    private String communityDate;

}
