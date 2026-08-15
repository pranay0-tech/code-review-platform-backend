
// package com.pranay.code_review_platform_backend.parser.dto;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// public class SourceResponse {

//     private String fileName;
//     private String className;
//     private String methodName;
//     public static Object builder() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'builder'");
//     }
// }



package com.pranay.code_review_platform_backend.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class SourceResponse {

    private String fileName;
    private String className;
    private String methodName;
}


