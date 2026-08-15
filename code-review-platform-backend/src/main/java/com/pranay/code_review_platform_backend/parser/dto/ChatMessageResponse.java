// package com.pranay.code_review_platform_backend.parser.dto;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// import java.util.List;

// @Getter
// @Setter
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// public class ChatMessageResponse {

//     private String answer;
//     private List<SourceResponse> sources;
//     public static Object builder() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'builder'");
//     }
// }

package com.pranay.code_review_platform_backend.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ChatMessageResponse {

    private String answer;
    private List<SourceResponse> sources;
}