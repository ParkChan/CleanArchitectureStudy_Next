# CleanArchitectureStudy

## History
- [이전 클린아키텍처 스터디](https://github.com/ParkChan/CleanArchitectureStudy/blob/master/README.md)

## 주요변경사항
- 클린 아키텍처 스터디에서 사용된 data/domain/app 멀티 모듈 구조를 패키지 구분으로 변경합니다.
- 앱 화면 단위를 feature module 단위로 분류합니다.
- ui 모듈 : feature에서 사용하는 공통 테마 설정 및 BaseActivity, BaseFragment 등이 있습니다.
- util 모듈 : startActivity, registerForActivityResult 

## [그림설명]
![그림1](https://user-images.githubusercontent.com/7857824/144235054-444c9824-5b66-4c02-9122-a56777621a7b.png)

---

## 학습목표
- Flow를 학습 합니다.[진행중]
- 멀티모듈을 패키지 구분으로 변경 합니다.[완료]
- 화면 단위별 Feature Module 단위로 분리 합니다.[완료]
- CI/CD [완료]

## PlayStore와 같은 하단 Footer 프로그래스바 보여주기
- ConcatAdapter를 사용한 Footer Progress Bar 적용

### 기타
- 네이버 OPEN API 검색 > 영화
  [링크주소]
  (https://developers.naver.com/docs/search/movie/)
- [How to check version updates when you are using buildSrc way of organizing Gradle dependencies?](https://medium.com/@kasem./how-to-check-version-updates-when-you-are-using-buildsrc-way-of-organizing-gradle-dependencies-5e659186c803)
  - 명령어 : 콘솔에서 gradle dependencyUpdates 입력 또는 Gradle 메뉴의 Task > help > dependencyUpdates
  
## 적용된 기술

### Unit Test Code
- Entity Test
- Repository Test
- Source Test
- ViewModel Test

### CI(Bitrise)
- Build Project
- ![140909093-7a0c660d-7288-4991-84e7-c8098724fbdd](https://user-images.githubusercontent.com/7857824/140920679-0e5115c3-61db-48dd-9419-68534650e398.png)


### CD
- 슬랙을 통해 앱을 배포

---

## 예제화면
![device-2021-11-09-020632 (1)](https://user-images.githubusercontent.com/7857824/140786792-f2b9fa4b-5538-4312-9abd-d0ff23886c63.png)

