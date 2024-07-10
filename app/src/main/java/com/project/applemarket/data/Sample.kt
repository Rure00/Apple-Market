package com.project.applemarket.data

import android.media.Image
import com.project.applemarket.R

object Sample {
    val userList = listOf(
        User(id = 1, nickname = "대현동", location = "서울 서대문구 창천동"),
        User(id = 2, nickname = "안마담", location = "인천 계양구 귤현동"),
        User(id = 3, nickname = "코코유", location = "수성구 범어동"),
        User(id = 4, nickname = "Nicole", location = "해운대구 우제2동"),
        User(id = 5, nickname = "절명", location = "연제구 연산제8동"),
        User(id = 6, nickname = "미니멀하게", location = "수원시 영통구 원천동"),
        User(id = 7, nickname = "굿리치", location = "남구 옥동"),
        User(id = 8, nickname = "난쉽", location = "동래구 온천제2동"),
        User(id = 9, nickname = "알뜰한", location = "원주시 명륜2동"),
        User(id = 10, nickname = "똑태현", location = "중구 동화동"),
    )

    val itemList = listOf(
        Item(id = 1, name = "선풍기", image = R.drawable.sample1),
        Item(id = 2, name = "김치냉장고", image = R.drawable.sample2),
        Item(id = 3, name = "샤넬 카드지갑", image = R.drawable.sample3),
        Item(id = 4, name = "금고", image = R.drawable.sample4),
        Item(id = 5, name = "갤럭시Z플립3", image = R.drawable.sample5),
        Item(id = 6, name = "프라다 복조리백", image = R.drawable.sample6),
        Item(id = 7, name = "울산 동해오션뷰 60평 복층 펜트하우스 1일 숙박권 펜션 힐링 숙소 별장", image = R.drawable.sample7),
        Item(id = 8, name = "샤넬 탑핸들 가방", image = R.drawable.sample8),
        Item(id = 9, name = "4행정 엔진분무기.", image = R.drawable.sample9),
        Item(id = 10, name = "셀린느 버킷 가방", image = R.drawable.sample10),
    )
    
    val postList = listOf(
        Post(
            id = 1, item = itemList[0], user = userList[0],
            title = "산진 한달된 선풍기 팝니다", body = "이사가서 필요가 없어졌어요 급하게 내놓습니다", price = 1000,
            interest = 13, chatNum = 25
        ),
        Post(
            id = 2, item = itemList[1], user = userList[1],
            title = "김치냉장고", body = "이사로 인해 내놔요", price = 20000,
            interest = 8, chatNum = 28
        ),
        Post(
            id = 2, item = itemList[1], user = userList[1],
            title = "샤넬 카드지갑", body = "고퀄지갑이구요\n사용감이 있어서 싸게 내어둡니다", price = 10000,
            interest = 23, chatNum = 5
        ),
        Post(
            id = 3, item = itemList[2], user = userList[2],
            title = "금고", body = "금고\\n떼서 가져가야함\\n대우월드마크센텀\\n미국이주관계로 싸게 팝니다", price = 10000,
            interest = 14, chatNum = 17
        ),
        Post(
            id = 4, item = itemList[3], user = userList[3],
            title = "갤럭시Z플립3 팝니다", body = "갤럭시 Z플립3 그린 팝니다\\n항시 케이스 씌워서 썻고 필름 한장챙겨드립니다\\n화면에 살짝 스크래치난거 말고 크게 이상은없습니다!", price = 150000,
            interest = 22, chatNum = 9
        ),
        Post(
            id = 5, item = itemList[4], user = userList[4],
            title = "프라다 복조리백", body = "까임 오염없고 상태 깨끗합니다\\n정품여부모름", price = 50000,
            interest = 25, chatNum = 16
        ),
        Post(
            id = 6, item = itemList[5], user = userList[5],
            title = "산진 한달된 선풍기 팝니다", body = "이사가서 필요가 없어졌어요 급하게 내놓습니다", price = 1000,
            interest = 13, chatNum = 25
        ),
        Post(
            id = 7, item = itemList[0], user = userList[0],
            title = "울산 동해오션뷰 60평 복층 펜트하우스 1일 숙박권 펜션 힐링 숙소 별장", body = "울산 동해바다뷰 60평 복층 펜트하우스 1일 숙박권\\n(에어컨이 없기에 낮은 가격으로 변경했으며 8월 초 가장 더운날 다녀가신 분 경우 시원했다고 잘 지내다 가셨습니다)\\n1. 인원: 6명 기준입니다. 1인 10,000원 추가요금\\n2. 장소: 북구 블루마시티, 32-33층\\n3. 취사도구, 침구류, 세면도구, 드라이기 2개, 선풍기 4대 구비\\n4. 예약방법: 예약금 50,000원 하시면 저희는 명함을 드리며 입실 오전 잔금 입금하시면 저희는 동.호수를 알려드리며 고객님은 예약자분 신분증 앞면 주민번호 뒷자리 가리시거나 지우시고 문자로 보내주시면 저희는 카드키를 우편함에 놓아 둡니다.\\n5. 33층 옥상 야외 테라스 있음, 가스버너 있음\\n6. 고기 굽기 가능\\n7. 입실 오후 3시, 오전 11시 퇴실, 정리, 정돈 , 밸브 잠금 부탁드립니다.\\n8. 층간소음 주의 부탁드립니다.\\n9. 방3개, 화장실3개, 비데 3개\\n10. 저희 집안이 쓰는 별장입니다.",
            price = 150000,
            interest = 142, chatNum = 54
        ),
        Post(
            id = 8, item = itemList[7], user = userList[7],
            title = "샤넬 탑핸들 가방", body = "샤넬 트랜디 CC 탑핸들 스몰 램스킨 블랙 금장 플랩백 !\\n + \"\\n\" + \"색상 : 블랙\\n\" + \"사이즈 : 25.5cm * 17.5cm * 8cm\\n\" + \"구성 : 본품더스트\\n\" + \"\\n\" + \"급하게 돈이 필요해서 팝니다 ㅠ ㅠ",
            price = 180000,
            interest = 31, chatNum = 7
        ),
        Post(
            id = 9, item = itemList[8], user = userList[8],
            title = "4행정 엔진분무기 판매합니다.", body = "3년전에 사서 한번 사용하고 그대로 둔 상태입니다. 요즘 사용은 안해봤습니다. 그래서 저렴하게 내 놓습니다. 중고라 반품은 어렵습니다.\\n",
            price = 30000,
            interest = 7, chatNum = 28
        ),
        Post(
            id = 10, item = itemList[9], user = userList[9],
            title = "셀린느 버킷 가방", body = "22년 신세계 대전 구매입니당\\n + \"셀린느 버킷백\\n\" + \"구매해서 몇번사용했어요\\n\" + \"까짐 스크래치 없습니다.\\n\" + \"타지역에서 보내는거라 택배로 진행합니당!\"",
            price = 190000,
            interest = 40, chatNum = 6
        ),
    )
}