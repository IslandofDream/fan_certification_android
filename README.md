# Fan Certification Android

# 개요

유튜버, 인스타 인플루언서, 기타 여러 셀럽들의 팬을 인증할 수 있는 어플리케이션이다. 언제부터 셀럽의 팬이었는지를 초기 설정 날짜 등을 통해, 셀럽을 얼마나 좋아하는지를 좋아요 숫자 등으로 나타내어 인증할 수 있도록 하였다. 본 ReadMe에서는 Android에 대해서만 다루겠다.

# 초기 설정

[노션 링크](https://www.notion.so/Fan-Certification-Android-a55ec9850b654d04b74b28d823ae9642)

Firebase Console: 

[Sign in - Google Accounts](https://console.firebase.google.com/u/0/project/fan-certification/overview)

1. Firebase Auth

본 서비스의 사용자 로그인을 위해 필요하다. 현재 이메일 로그인, Google Login, Apple Login을 지원하고 있지만, 이메일 로그인은 회원가입 절차가 조금 복잡하기 때문에 현재는 테스트 계정만 만들어서 사용하고 있다. 추후 이메일 로그인을 공식적으로 추가할 가능성이 있다. 앱 테스트 시에는 아래 계정을 사용하기를 권장한다.

ID: [test@test.com](mailto:test@test.com)

PW: 123456

2. Firebase Firestore

로그인과 더불어 가장 중요한 간단한 NoSQL 데이터베이스 서비스이다. 구조는 아래와 같다.

- Users (Collection)
    - 앞서 Firebase Auth의 사용자 고유 UID (Document)
        - celeb (array, map 데이터 3개 저장, Nullable) - array 개수는 3개로 제한. 추후 인앱결재 등을 통해 늘일 수 있음.
            - account (string)
                
                YouTube의 경우 채널 ID, Instagram의 경우 username
                
            - count (number)
                
                좋아요를 누른 횟수
                
            - platform (number)
                
                YouTube는 0, Instagram은 1, ...
                
            - recent (datetime)
                
                최근 좋아요 등을 누른 날짜와 시간, (좋아요 + 1)을 시도할 때 이 날짜가 현재 시간 대비 10분 이내이면 리젝한다.
                
            - since (datetime)
                
                셀럽의 팬으로 처음 등록한 날짜와 시간
                
- YouTube (Collection, 랭킹 등의 기능을 위해 필요)
    - YouTube Channel ID (Document)
        - follow (number)
            
            본 서비스 내 팔로워 수 (아주 정확하진 않아도 됨)
            
        - count (number)
            
            본 서비스 내 좋아요 수 (아주 정확하진 않아도 됨)
            
- Instagram (Collection, 랭킹 등의 기능을 위해 필요)
    - Instagram username (Document)
        - follow (number)
            
            본 서비스 내 팔로워 수 (아주 정확하진 않아도 됨)
            
        - count (number)
            
            본 서비스 내 좋아요 수 (아주 정확하진 않아도 됨)
            

(3. Firebase Functions)

추후 사용자가 늘어나 기본 데이터의 일괄 변경, 어뷰징 방지 등을 위한 코드가 필요할 경우 사용하게 될 가능성이 있다. 미리 node.js 기본 사용법에 익숙해지면 좋을 듯.

혹시라도 사용자가 기존보다 엄청 늘어나게 되면 AWS를 사용할 가능성도 있음.

- [ ]  Firebase에 대한 사용법, 설명은 Firebase의 공식 문서 참조

[Add Firebase to your Android project | Firebase Documentation](https://firebase.google.com/docs/android/setup?authuser=0)

4. 유튜브

유튜브 채널은 고유의 채널 ID가 있다. 그리고 이 ID를 검색하는 API는 아래와 같다. GET 방식이다.

https://www.googleapis.com/youtube/v3/search?part=id,snippet&type=channel&q={채널명 or 채널ID}&key=AIzaSyAMK7BBcUlJ81DjvkGL3mmPAZCcJeSjzRo

출력되는 json 파일 구조 중 일부는 아래와 같다. 다만 변동될 수도 있으니 직접 테스트해보는 걸 추천

- items (array)
    - snippet
        - thumbnails
            - default
                - url (string, 채널 프로필사진 url)
        - channelTitle (string)
            
            채널명
            
        - channelId (string)
            
            채널 ID: 검색 후 셀럽을 등록할 땐 이 채널 ID를 사용하도록 한다.
            

5. 인스타그램

인스타그램에는 고유의 사용자 ID와 있다. 하지만 현재는 공식적으로 모든 사용자의 ID를 검색할 수 있도록 하는 API는 없다. 그래서 임시로 username(고유 사용자 이름)을 사용하기로 한다. 인스타그램의 경우는 검색 기능을 보류한다. (혹시라도 고유 사용자 ID를 검색하는 API가 있다면 공유 부탁...)

# 화면 구성

[https://www.figma.com/file/O3Ig4q5gCj2FQJ292mcdCO/Fan-Certification?node-id=0%3A1](https://www.figma.com/file/O3Ig4q5gCj2FQJ292mcdCO/Fan-Certification?node-id=0%3A1)

# 테스트

테스터가 직접 keystore에서 key를 만들어 테스트한다. 다만, 실제 배포용 키는 내가 갖고 있으므로 실제 google play store 배포는 일단 지금은 나밖에 할 수가 없다. 양해 부탁.

# 마치며...

궁금한 것들은 언제든 나에게 연락하길 바란다. 쉽게 생각하면 엄청 쉽고, 어렵게 생각하면 매우 어려워질 수 있는 프로젝트라고 생각한다. 언제든 포기해도 좋다! 건투를 빈다!
