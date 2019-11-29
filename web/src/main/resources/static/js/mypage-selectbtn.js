function clickDownloadBtn() {
    var fileName = document.getElementById("select3").value;
    fileName = "downloadTemplate/" + fileName + ".docx";
    var iframe = document.getElementById("downloadIframe");
    iframe.src = fileName;
}

function onchangeSelect1(v) {
	document.getElementById("select3").innerHTML = '';
    var text = '';
    if (v == '인사') {
        text += '<option value="인사-인사관리">인사관리</option>';
        text += '<option value="인사-인사평가">인사평가</option>';
        text += '<option value="인사-급여">급여</option>';
        text += '<option value="인사-교육">교육</option>';
        text += '<option value="인사-노무">노무</option>';
        text += '<option value="인사-재해보상">재해보상</option>';
        document.getElementById("select2").innerHTML = text;

    } else if (v == '경영/기획') {
        text += '<option value="경영/기획-경영계획(전략)">경영계획(전략)</option>';
        text += '<option value="경영/기획-경영관리">경영관리</option>';
        text += '<option value="경영/기획-법무">법무</option>';
        text += '<option value="경영/기획-정보보안">정보보안</option>';
        text += '<option value="경영/기획-감사">감사</option>';
        document.getElementById("select2").innerHTML = text;

    } else if (v == '총무/재무') {

        text += '<option value="총무/재무-총무공통">총무공통</option>';
        text += '<option value="총무/재무-행사">행사</option>';
        text += '<option value="총무/재무-사옥관리">사옥관리</option>';
        text += '<option value="총무/재무-보안">보안</option>';
        text += '<option value="총무/재무-재무공통">재무공통</option>';
        document.getElementById("select2").innerHTML = text;

    } else if (v == '영업') {

        text += '<option value="영업-매출관리">매출관리</option>';
        text += '<option value="영업-신용관리">신용관리</option>';
        text += '<option value="영업-영업정책">영업정책</option>';
        text += '<option value="영업-영업전략">영업전략</option>';
        text += '<option value="영업-광고/판촉">광고/판촉</option>';
        text += '<option value="영업-시장조사">시장조사</option>';
        text += '<option value="영업-영업채널 관리">영업채널 관리</option>';
        document.getElementById("select2").innerHTML = text;

    } else if (v == '생산/제조') {

        text += '<option value="생산/제조-설계도면 관리">설계도면 관리</option>';
        text += '<option value="생산/제조-생산계획관리">생산계획관리</option>';
        text += '<option value="생산/제조-생산실적관리">생산실적관리</option>';
        text += '<option value="생산/제조-공정관리">공정관리</option>';
        text += '<option value="생산/제조-설비관리">설비관리</option>';
        text += '<option value="생산/제조-정비관리">정비관리</option>';
        text += '<option value="생산/제조-제조방법">제조방법</option>';
        document.getElementById("select2").innerHTML = text;

    } else if (v == 'R&D') {

        text += '<option value="R&D-연구기획">연구기획</option>';
        text += '<option value="R&D-연구개발">연구개발</option>';
        text += '<option value="R&D-연구데이터 및 결과">연구데이터 및 결과</option>';
        text += '<option value="R&D-기술이전 및 협력">기술이전 및 협력</option>';
        document.getElementById("select2").innerHTML = text;

    } else {
        document.getElementById("select2").innerHTML = '';
    }
}

function onchangeSelect2(v) {
    var text = '';
    if (v == '인사-인사관리') {
        text += '<option value="인사-인사관리-연간채용계획">연간 채용 계획</option>';
        text += '<option value="인사-인사관리-고용계약서">고용 계약서</option>';
        text += '<option value="인사-인사관리-고용추천서">고용 추천서</option>';
        text += '<option value="인사-인사관리-채용희망자개인정보">채용 희망자 개인정보</option>';
        text += '<option value="인사-인사관리-응시지원서관리">응시 지원서 관리</option>';
        document.getElementById("select3").innerHTML = text;

    } else if (v == '인사-인사평가') {
        text += '<option value="인사-인사평가-사내 인사평가 결과">사내 인사평가 결과</option>';
        text += '<option value="인사-인사평가-직무 분석 자료">직무 분석 자료</option>';
        text += '<option value="인사-인사평가-임원 업적 평가">임원 업적 평가</option>';
        document.getElementById("select3").innerHTML = text;

    } else   if (v == '인사-급여') {
        text += '<option value="인사-급여-인건비 현황">인건비 현황</option>';
        text += '<option value="인사-급여-인건비 분석 지표">인건비 분석 지표</option>';
        text += '<option value="인사-급여-급여 테이블">급여 테이블</option>';
        text += '<option value="인사-급여-입금 계좌 목록">입금 계좌 목록</option>';
        text += '<option value="인사-급여-급여 변동 내역서">급여 변동 내역서</option>';
        document.getElementById("select3").innerHTML = text;

    }  else  if (v == '인사-교육') {
        text += '<option value="인사-교육-교육 관련 제도 및 지침">교육 관련 제도 및 지침</option>';
        text += '<option value="인사-교육-교육 과정 개발">교육 과정 개발</option>';
        text += '<option value="인사-교육-중장기 교육계획">중장기 교육계획</option>';
        text += '<option value="인사-교육-연간 교육계획 및 결과보고서">연간 교육계획 및 결과보고서</option>';
        document.getElementById("select3").innerHTML = text;

    }  else  if (v == '인사-노무') {
        text += '<option value="인사-노무-단체 협약">단체 협약</option>';
        text += '<option value="인사-노무-임금 및 단체협약 시행 내용 관리">임금 및 단체협약 시행 내용 관리</option>';
        document.getElementById("select3").innerHTML = text;

    }  else  if (v == '인사-재해보상') {
        text += '<option value="인사-재해보상-보험사고 관련 보고">보험사고 관련 보고</option>';
        text += '<option value="인사-재해보상-보험업무 및 기타 관리 문서">보험업무 및 기타 관리 문서</option>';
        text += '<option value="인사-재해보상-산재 보상관리">산재 보상관리</option>';
        document.getElementById("select3").innerHTML = text;

  } else if (v == '경영/기획-경영계획(전략)') {
      text += '<option value="경영/기획-경영계획(전략)-경영(전략) 목표 추진 계획">경영(전략) 목표 추진 계획</option>';
      document.getElementById("select3").innerHTML = text;

  } else if (v == '경영/기획-경영관리') {
      text += '<option value="경영/기획-경영관리-경영실적 분석 자료">경영실적 분석 자료</option>';
      document.getElementById("select3").innerHTML = text;

  }
  else if (v == '경영/기획-법무') {
      text += '<option value="경영/기획-법무-회사 관련 지침">회사 관련 지침</option>';
      document.getElementById("select3").innerHTML = text;

  }
  else if (v == '경영/기획-정보보안') {
      text += '<option value="경영/기획-정보보안-기밀정보 보호 방침">기밀정보 보호 방침</option>';
      document.getElementById("select3").innerHTML = text;

  }
  else if (v == '경영/기획-감사') {
      text += '<option value="경영/기획-감사-내부 정기감사">내부 정기감사</option>';
      document.getElementById("select3").innerHTML = text;

  }
  else if (v == '총무/재무-총무공통') {
      text += '<option value="총무/재무-총무공통-비밀문서 복사,소각 대장">비밀문서 복사,소각 대장</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '총무/재무-행사') {
      text += '<option value="총무/재무-행사-행사계획">행사계획</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '총무/재무-사옥관리') {
      text += '<option value="총무/재무-사옥관리-사무실 배치도">사무실 배치도</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '총무/재무-보안') {
      text += '<option value="총무/재무-보안-통제구역 출입자 명부">통제구역 출입자 명부</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '총무/재무-재무공통') {
      text += '<option value="총무/재무-재무공통-자금 조달 계획">자금 조달 계획</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '총무/재무-재무공통') {
      text += '<option value="총무/재무-재무공통-자금 조달 계획">자금 조달 계획</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '영업-매출관리') {
      text += '<option value="영업-매출관리-판매실적 전망">판매실적 전망</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '영업-신용관리') {
      text += '<option value="영업-신용관리-신용거래 관리">신용거래 관리</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '영업-영업정책') {
      text += '<option value="영업-영업정책-영업정책 관련 서류">영업정책 관련 서류</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '영업-영업전략') {
      text += '<option value="영업-영업전략-시장전략 보고서">시장전략 보고서</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '영업-광고/판촉') {
      text += '<option value="영업-광고/판촉-광고 기획문서">광고 기획문서</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '영업-시장조사') {
      text += '<option value="영업-시장조사-시장 현황 조사">시장 현황 조사</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '영업-영업채널 관리') {
      text += '<option value="영업-영업채널 관리-영업 관련 고객리스트">영업 관련 고객리스트</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '생산/제조-설계도면 관리') {
      text += '<option value="생산/제조-설계도면 관리-중요공장 설계도">중요공장 설계도</option>';
      text += '<option value="생산/제조-설계도면 관리-설계도면 관리계획">설계도면 관리계획</option>';
      text += '<option value="생산/제조-설계도면 관리-제품생산라인 설계도">제품생산라인 설계도</option>';
      text += '<option value="생산/제조-설계도면 관리-공정설계도">공정설계도</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '생산/제조-생산계획관리') {
      text += '<option value="생산/제조-생산계획관리-기간별 생산계획 관리">기간별 생산계획 관리</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '생산/제조-생산실적관리') {
      text += '<option value="생산/제조-생산실적관리-생산계획 대비 실적">생산계획 대비 실적</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '생산/제조-공정관리') {
      text += '<option value="생산/제조-공정관리-공정계획">공정계획</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '생산/제조-설비관리') {
      text += '<option value="생산/제조-설비관리-기계 장치의 배치도">기계 장치의 배치도</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '생산/제조-정비관리') {
      text += '<option value="생산/제조-정비관리-정기 점검보수 기본계획">정기 점검보수 기본계획</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == '생산/제조-제조방법') {
      text += '<option value="생산/제조-제조방법-제품가공/조립/제조방법">제품가공/조립/제조방법</option>';
      text += '<option value="생산/제조-제조방법-물질배합방법">물질배합방법</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == 'R&D-연구기획') {
      text += '<option value="R&D-연구기획-연구개발 예산 수립">연구개발 예산 수립</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == 'R&D-연구개발') {
      text += '<option value="R&D-연구개발-연구개발 보고서 관리">연구개발 보고서 관리</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == 'R&D-연구데이터 및 결과') {
      text += '<option value="R&D-연구데이터 및 결과-연구결과(기술논문, 특허, 시제품 테스트)">연구결과(기술논문, 특허, 시제품 테스트)</option>';
      document.getElementById("select3").innerHTML = text;
  }
  else if (v == 'R&D-기술이전 및 협력') {
      text += '<option value="R&D-기술이전 및 협력-연구과제 실용화 계획">연구과제 실용화 계획</option>';
      document.getElementById("select3").innerHTML = text;
  }
   else {
        document.getElementById("select3").innerHTML = '';
  }
}
