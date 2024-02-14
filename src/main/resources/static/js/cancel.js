function mCancel(MNO) {
    console.log('MNO = ', MNO);
    if(confirm('회원탈퇴를 하시겠습니까?')) {
        let mPw = prompt('비밀번호를 입력해주세요');
        $.ajax({
            url: '/mpwCheck',
            type: 'post',
            data: {
                'MNO': MNO,
                'mPw': mPw
            },
            dataType: 'text',
            success: function(result) {
                console.log('result = ', result);
                if(result === 'OK') {
                    alert('회원탈퇴가 정상적으로 처리되었습니다. 안녕히가세요!');
                    let output = `
                        <form action="/toIndex" method="POST" enctype="multipart/form-data" name="deleteForm">
                        </form>
                   `;
                    $('#output').html(output);
                    deleteForm.submit();
                } else if(result === 'NO') {
                    alert('비밀번호가 다릅니다.');
                }
            },
            error: function() {
                alert('mpwCheck 함수 통신 실패');
            }
        });
    }
}

function cCancel(comNo) {
    console.log('comNo = ', comNo);
    if(confirm('회원탈퇴를 하시겠습니까?')) {
        let cPw = prompt('비밀번호를 입력해주세요');
        $.ajax({
            url: '/cpwCheck',
            type: 'post',
            data: {
                'comNo': comNo,
                'cPw': cPw
            },
            dataType: 'text',
            success: function(result) {
                console.log('result = ', result);
                if(result === 'OK') {
                    alert('회원탈퇴가 정상적으로 처리되었습니다. 안녕히가세요!');
                    let output = `
                        <form action="/toIndex" method="POST" enctype="multipart/form-data" name="deleteForm">
                        </form>
                   `;
                    $('#output').html(output);
                    deleteForm.submit();
                } else if(result === 'NO') {
                    alert('비밀번호가 다릅니다.');
                }
            },
            error: function() {
                alert('cpwCheck 함수 통신 실패');
            }
        });
    }

}