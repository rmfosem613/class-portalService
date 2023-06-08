// Create elements
const h2 = document.createElement('h2');
h2.textContent = '카테고리';

const ul = document.createElement('ul');
ul.classList.add('category-list');

const categories = ['전체', '경상대학', '공과대학', '인문대학', '의과대학', '자연대학', '해양대학'];

categories.forEach(category => {
    const li = document.createElement('li');
    const a = document.createElement('a');
    a.href = '/posts';
    a.textContent = category;

    li.appendChild(a);
    ul.appendChild(li);
});

// Append to container
const menuContainer = document.querySelector('.menu');
menuContainer.appendChild(h2);
menuContainer.appendChild(ul);