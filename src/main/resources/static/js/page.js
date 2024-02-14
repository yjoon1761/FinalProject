document.addEventListener('DOMContentLoaded', function () {  // 페이지 로드시 실행되는 함수
    $(function () {
        // $(".left_sub_menu").hide();
        $(".has_sub").click(function () {
            $(".left_sub_menu").fadeToggle(300);
        });
        // 왼쪽메뉴 드롭다운
        $(".sub_menu ul.small_menu").hide();
        $(".sub_menu ul.big_menu").click(function () {
            $("ul", this).slideToggle(300);
        });
    });
});

// 개인 회원 페이지 //
function join() {
    location.href = "/mJoinForm";           // 회원가입 페이지로 이동
}

function mfindId() {
    location.href = "/mIdForm";             // 개인회원 아이디 찾기 페이지로 이동
}

function idResult(){
    location.href = "/idResult";           // 개인회원 아이디 결과 페이지로 이동
}

function mIdFind() {
    location.href = "/mIdFind";             // 개인회원 아이디 찾기
}

function mfindPw() {
    location.href = "/mPwForm";             // 비밀번호 찾기 페이지로 이동
}

function mPwFind() {
    location.href = "/mPwFind";             // 비밀번호 찾기
}

function mListForm() {
    location.href = "/mListForm";           // 회원목록 페이지로 이동
}

function cListForm() {
    location.href = "/cListForm";           // 기업목록 페이지로 이동
}

function home(){
    location.href= "/";                     // 인덱스 페이지로 이동
}

function pView() {
    location.href= "/pView";               // 마이펫 상세보기 페이지로 이동
}

function pModiForm() {
    location.href = "/pModiForm";          //마이펫 정보 수정 페이지로 이동
}

// 게시판
function bList(x) {
    location.href= "/bList/" + x;           // 게시판 페이지로 이동
}

function bRegiForm() {
    location.href= "/bRegiForm";           // 게시물 등록 페이지로 이동
}

function bView(){
    location.href= "/bView";               // 게시판 상세보기 페이지로 이동
}

function gView(){
    location.href= "/gView";               // 갤러리 상세보기 페이지로 이동
}

function bModiForm() {
    location.href= "/bModiForm";           // 게시물 수정 페이지로 이동
}

// 개인 회원 메인 끝 //

// 기업 회원 메인 //

function cfindId() {
    location.href = "/cIdForm";             // 기업회원 아이디 찾기
}

function cfindPw() {
    location.href = "/cPwForm";             // 기업 비밀번호 찾기 페이지로 이동
}

// 기업 회원 페이지 끝//

// 기업 차트 시작//

function recRegister() {
    location.href = "/recRegister";           // 기업 차트작성 이동
}

function crecList() {
    location.href = "/crecList";        // 기업 차트목록 페이지로 이동
}


// 기업 차트 끝//

function rResForm(x) {
    console.log('x = ', x);
    if(x === 1) {
        alert('로그인 후 이용가능합니다');
    } else if(x === 2) {
        alert('기업회원은 이용할 수 없는 메뉴입니다')
    } else if(x === 3) {
        location.href = "/rResForm";
    }
}

function pRegiForm(x) {
    console.log('x = ', x);
    if(x === 1) {
        alert('로그인 후 이용가능합니다');
    } else if(x === 2) {
        alert('기업회원은 이용할 수 없는 메뉴입니다')
    } else if(x === 3) {
        location.href = "/pRegiForm";
    }
}
