fetch('/json/department.json')
    .then(response => response.json())
    .then(data => {
        const departmentSelect = document.querySelector('select[name="department"]');
        const majorSelect = document.querySelector('select[name="major"]');

        // departmentSelect에 option 추가
        data.forEach(department => {
            const option = document.createElement('option');
            option.value = department.id;
            option.textContent = department.name;
            departmentSelect.appendChild(option);
        });

        // departmentSelect의 값이 변경되었을 때 majorSelect option 변경
        departmentSelect.addEventListener('change', () => {
            // 선택된 department의 id 값을 가져옴
            const selectedDepartmentId = parseInt(departmentSelect.value);

            // majorSelect option 초기화
            majorSelect.innerHTML = '<option value="">학과 선택</option>';

            // 선택된 department의 id 값에 따라 majorSelect option 설정
            data.forEach(department => {
                if (department.id === selectedDepartmentId) {
                    fetch(`/json/${department.id}.json`)
                        .then(response => response.json())
                        .then(majorData => {
                            majorData.forEach(major => {
                                const option = document.createElement('option');
                                option.value = major.name;
                                option.textContent = major.name;
                                majorSelect.appendChild(option);
                            });
                        })
                        .catch(error => {
                            console.error('Error fetching JSON:', error);
                        });
                }
            });
        });
    })
    .catch(error => {
        console.error('Error fetching JSON:', error);
    });