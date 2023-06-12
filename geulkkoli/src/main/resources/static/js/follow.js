function followButtonHandler() {
    var followButton = document.getElementById('btn-follow');
    if (followButton) {
        followButton.addEventListener('click', function() {
            var email = document.getElementById('email').textContent;
            var url = '/follow/' + email;

            fetch(url, {
                method: 'GET', // or 'POST'
            })
                .then(function(response) {
                    if (response.ok) {
                        var unfollowButton = document.createElement('button');
                        unfollowButton.id = 'btn-unfollow';
                        unfollowButton.className = 'w-100 btn btn-primary';
                        unfollowButton.setAttribute('type', 'button');
                        unfollowButton.textContent = '구독 취소';

                        var parentElement = followButton.parentNode;
                        if (parentElement) {
                            parentElement.replaceChild(unfollowButton, followButton);
                        }
                        unFollowButtonHandler();
                    }
                })
                .catch(function(error) {
                    console.log('Error:', error);
                });
        });
    }
}

function unFollowButtonHandler() {
    var unfollowButton = document.getElementById('btn-unfollow');
    if (unfollowButton) {
        unfollowButton.addEventListener('click', function() {
            var email = document.getElementById('email').textContent;
            var url = '/unfollow/' + email;

            fetch(url, {
                method: 'GET', // or 'POST'
            })
                .then(function(response) {
                    if (response.ok) {
                        var followButton = document.createElement('button');
                        followButton.id = 'btn-follow';
                        followButton.className = 'w-100 btn btn-primary';
                        followButton.setAttribute('type', 'button');
                        followButton.textContent = '구독하기';

                        var parentElement = unfollowButton.parentNode;
                        if (parentElement) {
                            parentElement.replaceChild(followButton, unfollowButton);
                        }
                        followButtonHandler();
                    }
                })
                .catch(function(error) {
                    console.log('Error:', error);
                });
        });
    }
}

document.addEventListener('DOMContentLoaded', function() {
    followButtonHandler();
    unFollowButtonHandler();
});