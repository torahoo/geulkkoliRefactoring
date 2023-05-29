commentEditButtonInit();
commentDeleteButtonInit();

let commentBodyBox = document.getElementById('commentBody');

// 댓글 작성 버튼 이벤트 등록
document.getElementById('commentSubmit').addEventListener('click', function (ev) {

    ev.preventDefault();
    const commentForm = document.getElementById('comment');

    const str_json = JSON.stringify(
        Object.fromEntries(
            new FormData(commentForm)));

    if(commentBodyBox.value) {
        fetch(commentForm.getAttribute('action'), {
            method: 'POST',
            headers: { 'Content-Type' : 'application/json' },
            body: str_json
        })
            .then((response) => {
                if (response.ok)
                    response.json().then((json) => {
                        if (json) commentRemake(json);
                    });
                else
                    response.text().then((text) => {
                        let validText = document.createElement('p');
                        validText.innerText = text;
                        validText.style.color = '#dc3545';
                        commentBodyBox.parentElement.appendChild(validText);
                    });
                /*else if (response.status == 202) {
                    console.log('status = 202');
                    console.log(commentBodyBox.value);
                    commentBodyBox.style.borderColor = '#dc3545';
                    const validText = document.createElement('p');
                    validText.style.color = '#dc3545';
                    validText.innerText = '댓글은 '
                    commentBodyBox.parentElement.appendChild(validText);
                } else {
                    throw new Error();
                }*/
            });

    }
});

// 댓글 수정 버튼 이벤트 초기화
function commentEditButtonInit() {
    let commentEdits = document.getElementsByClassName('commentEdit');
    for (let commentEdit of commentEdits) {
        commentEdit.removeEventListener('click', commentEditButtonEvent);
        commentEdit.addEventListener('click', commentEditButtonEvent);
    }
} //commentEditButtonInit

// 댓글 삭제 버튼 이벤트 초기화
function commentDeleteButtonInit() {
    for ( let commentDeleteButton of document.getElementsByClassName("commentDelete")) {
        commentDeleteButton.removeEventListener('click', commentDeleteButtonEvent);
        commentDeleteButton.addEventListener('click', commentDeleteButtonEvent);
    }
} //commentDeleteButtonInit

// 댓글 수정 버튼 이벤트
function commentEditButtonEvent(event) {
    event.preventDefault();

    let target = event.target;
    let cardBody = target.closest('.card-body');
    let textarea = document.createElement('textarea');
    let p = cardBody.firstElementChild;
    let commentBodyText = p.innerText;
    let transButton = target.closest('div').querySelector('.commentDelete');

    textarea.setAttribute('class', 'col form-control')
    textarea.value = commentBodyText;
    cardBody.replaceChild(textarea, p);

    target.removeEventListener('click', commentEditButtonEvent);
    target.innerText = '등록';
    transButton.innerText = '취소';

    transButton.addEventListener('click', function() {
        p.innerText = commentBodyText;
        cardBody.replaceChild(p, textarea);
        target.innerText = '수정';
        transButton.innerText = '삭제';
        target.addEventListener('click', commentEditButtonEvent);
    });

    target.addEventListener('click', function (ev) {
        ev.preventDefault();

        if (target.innerText != '등록')
            return;

        let formData = new FormData();
        formData.set('commentId', target.closest('.card').querySelector('h5').id);
        formData.set('commentBody', target.closest('.card-body').firstElementChild.value);

        fetch('/comments/' + postId, {
            method: 'PUT',
            headers: { 'Content-Type' : 'application/json'},
            body: JSON.stringify(Object.fromEntries(formData))
        })
            .then((response) => response.json())
            .then((list) => {
                commentRemake(list);
            });
    });
} //commentEditButtonEvent

// 댓글 삭제 버튼 이벤트
function commentDeleteButtonEvent(ev) {
    ev.preventDefault();

    let commentDeleteButton = ev.target;
    if (commentDeleteButton.innerText != '삭제')
        return;

    if (!confirm('정말 삭제하시겠습니까?')) {
        return;
    }

    let formData = new FormData();
    formData.set('commentId', commentDeleteButton.closest('.card').querySelector('h5').id);

    fetch('/comments', {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(Object.fromEntries(formData))
    }).then((response) => {
        if (response.status == 200) {
            commentDeleteButton.closest('.card').remove();
        }
    });
} //commentDeleteButtonEvent

//댓글들을 새로 작성한다.
function commentRemake(list) {
    const postComment = document.getElementById('postComment');
    while (postComment.hasChildNodes()) {
        postComment.firstChild.remove();
    }
    for (let newComment of list) {
        let forComment = document.createElement('div');
        forComment.setAttribute('class', 'mt-2 card w-100');
        forComment.style.width = '18rem';
        let forCommentHeader = document.createElement('div');
        forCommentHeader.setAttribute('class', 'card-header row justify-content-md-center');
        let forCommentBody = document.createElement('div');
        forCommentBody.setAttribute('class', 'card-body');

        let userNickName = document.createElement('h5');
        userNickName.setAttribute('id', newComment.commentId);
        userNickName.setAttribute('class', 'card-title col');
        userNickName.innerText = newComment.nickName;

        let writeDate = document.createElement('h6');
        writeDate.setAttribute('class', 'card-subtitle col-md-auto');
        writeDate.innerText = newComment.date;

        let newCommentBody = document.createElement('p');
        newCommentBody.setAttribute('class', 'card-text commentBody');
        newCommentBody.innerText = newComment.commentBody;

        let buttonDiv = document.createElement('div');
        buttonDiv.setAttribute('class', 'mt-1 d-grid gap-2 d-md-flex justify-content-md-end');

        let editButton;
        let deleteButton;
        if (newComment.nickName == validName) {
            editButton = document.createElement('button');
            editButton.setAttribute('class', 'btn btn-primary btn-sm commentEdit');
            editButton.innerText = '수정';

            deleteButton = document.createElement('button');
            deleteButton.setAttribute('class', 'btn btn-primary btn-sm commentDelete');
            deleteButton.innerText = '삭제';

            buttonDiv.appendChild(editButton);
            buttonDiv.appendChild(deleteButton);
        }

        forComment.appendChild(forCommentHeader);
        forCommentHeader.appendChild(userNickName);
        forCommentHeader.appendChild(writeDate);

        forComment.appendChild(forCommentBody);
        forCommentBody.appendChild(newCommentBody);
        forCommentBody.appendChild(buttonDiv);

        postComment.appendChild(forComment);

        commentBodyBox.value = "";

    }
    commentEditButtonInit();
    commentDeleteButtonInit();
} //commentRemake