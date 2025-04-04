package com.temi.ark.utils.helpers

class StringHelper {
    fun convertLocate (locate: String): String {
        when (locate) {
            "아 001.3-김74ㅊ-1" -> return "아동0-1"
            "아 082-생12ㄹ-1" -> return "아동0-2"
            "아 082-텐884ㄱ-1" -> return "아동1-1"
            "아 108-철92ㅇ-2" -> return "아동1-2"
            "아 308-초27ㅁ-9" -> return "아동2-1"
            "아 388.111-잘72ㅁ-1" -> return "아동2-2"
            "아 408-초27ㅇ-2" -> return "아동3-1"
            "아 500-황78ㄱ" -> return "아동3-2"
            "아 802-송64ㅅ" -> return "아동4-1"
            "아 808.9-북34ㅂ-1" -> return "아동4-2"
            "아 808.9-이63ㄱ-5" -> return "아동5-1"
            "아 808.9-하197ㄱ-4" -> return "아동5-2"
            "아 812.6-어298ㄴ-2" -> return "아동6-1"
            "아 813.8-안54ㄱ" -> return "아동6-2"
            "아 833.8-오35ㄹ" -> return "아동7-1"
            "아 843-파66ㅇ" -> return "아동7-2"
            "아 909-김52ㅌ-5" -> return "아동8-1"
            "아 911-용54ㅅ-5" -> return "아동8-2"
            "아 911.008-작12ㅎ-2" -> return "아동9-1"
            "아 990.8-인36ㅇ-6" -> return "아동9-2"
            "유 004.73-레28ㅇ-1" -> return "유아자료실"
            "유 808.3-상52ㅇ-10" -> return "유아800-1"
            "유 808.9-네44ㅅ-3" -> return "유아800-2"
            "유 808.9-바292ㅊ-102" -> return "유아800-3"
            "유 808.9-사12ㅂ-9" -> return "유아800-4"
            "유 813.8-이74ㅅ" -> return "유아800-5"
            "802-간95ㄷ" -> return "일반800-1"
            "813.5-교15ㅇ-2" -> return "일반800-2"
            "818-강74ㄱ" -> return "일반800-3"
            "833.6-기52ㅇ" -> return "일반800-4"
            "001.2-김52ㄴ" -> return "총류"
            "109.9-김68ㅊ" -> return "철학"
            "200-김45ㅈ" -> return "종교"
            "321.542-박84ㅇ" -> return "사회과학"
            "408-과92ㅍ-15" -> return "자연과학"
            "504-박73ㄱ" -> return "기술과학"
            "598.1-이38ㄸ" -> return "예술"
            "701.08-임65ㅍ" -> return "언어"
            "910-굽58ㅂ-4" -> return "역사"
            "특화 034-브298ㅎ-1" -> return "우주특화아동 ZBJ"
            "특화 001.442-강66ㅇ" -> return "우주특화아동 ZBE"
            "W 082-A759f-7" -> return "서양도서1"
            "W 843-D632d" -> return "서양도서2"
            "W 843-P153r" -> return "서양도서3"
            "W 843-S672s" -> return "서양도서4"
        }

        return ""
    }
}