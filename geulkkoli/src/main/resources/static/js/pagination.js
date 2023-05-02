const blockCount = 5;
const block = Math.floor(currentPage / blockCount) * blockCount;

const ul = document.getElementById('pagination');
const li = document.getElementsByClassName('page-item');

for (let page = block, index = 0;
     page < (block + blockCount) & page < lastPage;
     ++page, ++index) {

    const liPage = document.createElement('li');
    liPage.className = 'page-item';
    if (page === Number(currentPage)) {
        liPage.className += ' active';
    }

    const a = document.createElement('a');
    a.className = 'page-link';
    a.text = String(page + 1);
    a.href = makeURI(a.text - 1, size);

    liPage.append(a);
    ul.appendChild(liPage);
}

isVisible(isFirst, li[0]);
li[0].querySelector('a').href = makeURI(currentPage - 1, size);

isVisible(isLast, li[1]);
li[1].querySelector('a').href = makeURI(currentPage + 1, size);

ul.appendChild(li[1]);

function isVisible(bool, obj) {
    bool ? obj.style.visibility = 'hidden' : obj.style.visibility = 'visible'
}

function makeURI(page, size) {
    return '/post/list?page=' + page + '&size=' + size;
}