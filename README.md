# Fan Certification Android

# 개요

유튜버의 팬임을 인증할 수 있는 어플리케이션이다. 언제부터 셀럽의 팬이었는지를 초기 설정 날짜 등을 통해, 셀럽을 얼마나 좋아하는지를 좋아요 숫자 등으로 나타내어 인증할 수 있도록 하였다. 

**출시링크** : https://play.google.com/store/apps/details?id=com.fancertification.www

![vertical](https://user-images.githubusercontent.com/70648111/161492490-b347f898-7e45-4537-898f-5692fe143ee5.png)


# 개발의도
단순 유튜버를 구독하는 것을 넘어서 구독한 유튜버중에서도 내가 특히 좋아하고 원하는 유튜버들의 상세정보를 한눈에 모아볼 수 있고 또한 내가 우리 어플에서 얼마나 오랫동안 해당 유튜버를 구독했는지 경과를 알 수 있어서 이사람에 대한 나의 관심이 언제부터 이어져있는 가를 확인하여 Fan certification이라는 말처럼 개개인의 팬이 유튜버에 대한 팬임을 인증을 할 수 있도록 목적을 두었다.

## 기능개요

- 팬 인증(북마크 추가)

- 유튜버 상세정보 확인 (유튜브 API를 통한 크리에이터에 대한 통계 및 자신의 구독기간 확인)

- 채널 검색(사칭 채널 방지를 위해 youtube DataAPI를 통해 검색결과와 함께 각 채널의 총 구독자수 및 채널설명을 보여준다.)

# 시연 영상

## 유튜버 검색 및 저장 / 북마크화면에서 유튜버 정보 확인

https://user-images.githubusercontent.com/70648111/161674160-884a053d-fc4d-4718-a3f5-11a4d3a3d847.mp4

## 유튜버 검색 및 삭제

https://user-images.githubusercontent.com/70648111/161674168-42d76413-2ba1-4b7f-9169-0610ee45f9ad.mp4

# 확장성

- 앞으로 더 확장을 하게 된다면, Firebase 를 이용한 회원가입기능,
- Local DB가 아닌 회원별 FireStore를 통한 데이터관리
- 추가한 유튜버에 대한 어플내 사용자들의 좋아요 count 및 랭킹산정
- 유튜버에 대한 추가정보 제공(가장 조회수가 높은 동영상, 채널개설일등)
- 해당 유튜버에 대한 나만의 정보 추가 (가장 좋아하는 동영상 추가, 해당 유튜버에게 하고싶은 말)


# 초기 설정

[노션 링크](https://www.notion.so/Fan-Certification-Android-a55ec9850b654d04b74b28d823ae9642)


# 화면 구성

[https://www.figma.com/file/O3Ig4q5gCj2FQJ292mcdCO/Fan-Certification?node-id=0%3A1](https://www.figma.com/file/O3Ig4q5gCj2FQJ292mcdCO/Fan-Certification?node-id=0%3A1)

