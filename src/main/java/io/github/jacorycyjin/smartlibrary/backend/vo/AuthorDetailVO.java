package io.github.jacorycyjin.smartlibrary.backend.vo;

import io.github.jacorycyjin.smartlibrary.backend.dto.AuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 作者信息 VO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class AuthorDetailVO extends AuthorPublicVO {

    /**
     * 来源
     */
    private String sourceOrigin;

    /**
     * 来源链接
     */
    private String sourceUrl;

    public static AuthorDetailVO fromDTO(AuthorDTO dto) {
        if (dto == null) {
            return null;
        }
        return AuthorDetailVO.builder()
                .authorId(dto.getAuthorId())
                .name(dto.getName())
                .originalName(dto.getOriginalName())
                .country(dto.getCountry())
                .photoUrl(dto.getPhotoUrl())
                .description(dto.getDescription())
                .sourceOrigin(dto.getSourceOrigin())
                .sourceUrl(dto.getSourceUrl())
                .build();
    }
}
