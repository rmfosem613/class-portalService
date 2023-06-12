// Create elements
const h2 = document.createElement('h2');
h2.textContent = '카테고리';

const ul = document.createElement('ul');
ul.classList.add('category-list');

const categories = ['전체','경상대학', '공과대학', '인문대학', '의과대학', '자연과학대학', '해양과학대학'];

// Load department.json
fetch('../json/department.json')
    .then(response => response.json())
    .then(departmentJson => {
        categories.forEach(category => {
            const li = document.createElement('li');
            const a = document.createElement('a');

            // Check if category exists in department.json
            if (departmentJson.findIndex(item => item.name === category) !== -1) {
                a.href = '/posts/' + category;
            } else {
                a.href = '/posts';
            }

            a.textContent = category;

            li.appendChild(a);
            ul.appendChild(li);
        });
    })
    .catch(error => {
        console.error('Failed to load department.json:', error);
    });

// Append to container
const menuContainer = document.querySelector('.menu');
menuContainer.appendChild(h2);
menuContainer.appendChild(ul);