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

function recRegiForm() {
    console.log('comNo = ', comNo);
    let dId = prompt("담당의 번호를 입력하세요");
    if(dId.length !== 0) {
        $.ajax({
            url: '/dIdCheck2',
            type: 'post',
            data: {
                'comNo': comNo,
                'dId': dId
            },
            dataType: 'text',
            success: function(result) {
                console.log(result);
                if(result === 'OK') {
                    location.href = `/recRegiForm/${dId}`;
                } else {
                    alert('등록된 의사번호가 아닙니다');
                }
            },
            error: function() {
                alert('dIdCheck 함수 통신 실패');
            }
        });
    }
}