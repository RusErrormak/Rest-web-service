
document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/admin')
        .then(response => response.json())
        .then(users => {
            updateUsersTable(users);
        })
        .catch(error => console.error('Ошибка при загрузке пользователей:', error));

    fetch('/api/user/info')
        .then(response => response.json())
        .then(user => {

            const navbarBrand = document.querySelector('.navbar-brand');
            if (navbarBrand) {
                navbarBrand.innerHTML = `${user.email} with roles: 
                                 ${user.roles.map(role => role.name).join(', ')}`;
            }
        });

        const editForm = document.getElementById('editUserForm');
        if (editForm) {
            editForm.addEventListener('submit', editUser);
        }

        const addForm = document.getElementById('addUserForm');
        if (addForm) {
            addForm.addEventListener('submit', addNewUser);
        }
    });

function updateUsersTable(users) {
    const usersTableBody = document.querySelector('#usersTableBody');
    if (usersTableBody) {
        usersTableBody.innerHTML = users.map(user => `
        <tr class="border-top" data-user-id="${user.id}">
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(role => role.name).join(', ')}</td>
            <td><button class="btn btn-info" onclick="editUser(${user.id})">Edit</button></td>    
            <td><button class="btn btn-danger" onclick="deleteUser(${user.id})">Delete</button></td>
           
        </tr>
    `).join('');
    }
}

function editUser(userId) {

    const row = document.querySelector(`tr[data-user-id="${userId}"]`);
    const username = row.cells[1].textContent;
    const email = row.cells[2].textContent;
    const roles = row.cells[3].textContent.split(', ');

    document.getElementById('editUserId').value = userId;
    document.getElementById('editUsername').value = username;
    document.getElementById('editEmail').value = email;

    const roleSelect = document.getElementById('editRoles');
    Array.from(roleSelect.options).forEach(option => {
        option.selected = roles.includes(option.value);
    });

    $('#editModal').modal('show');
}

document.getElementById('editUserForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const userId = document.getElementById('editUserId').value;
    const userData = {
        username: document.getElementById('editUsername').value,
        email: document.getElementById('editEmail').value,
        password: document.getElementById('editPassword').value,
        roles: Array.from(document.getElementById('editRoles').selectedOptions)
            .map(option => ({name: option.value}))
    };

    fetch(`/api/admin/update/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
        .then(response => response.json())
        .then(updatedUser => {
            const row = document.querySelector(`tr[data-user-id="${updatedUser.id}"]`);
            if (row) {
                row.innerHTML = `
                <td>${updatedUser.id}</td>
                <td>${updatedUser.username}</td>
                <td>${updatedUser.email}</td>
                <td>${updatedUser.roles.map(role => role.name).join(', ')}</td>
                <td><button class="btn btn-info" onclick="editUser(${updatedUser.id})">Edit</button></td>    
                <td><button class="btn btn-danger" onclick="deleteUser(${updatedUser.id})">Delete</button></td>
            `;
            }
            fetch('/api/admin')
                .then(response => response.json())
                .then(users => {
                    updateUsersTable(users);
                    $('#editModal').modal('hide');
                });
        });
    });



function deleteUser(userId) {

    const row = document.querySelector(`tr[data-user-id="${userId}"]`);
    document.getElementById('deleteUserId').value = userId;
    document.getElementById('deleteUsername').value = row.cells[1].textContent;
    document.getElementById('deleteEmail').value = row.cells[2].textContent;


    $('#deleteModal').modal('show');
}

document.getElementById('deleteUserForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const userId = document.getElementById('deleteUserId').value;

    fetch(`/api/admin/delete/${userId}`, {
        method: 'DELETE'
    })
        .then(response => response.json())
        .then(users => {
            updateUsersTable(users);
            $('#deleteModal').modal('hide');
        });
});



function addNewUser(event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);

    const userData = {
        username: formData.get('username'),
        email: formData.get('email'),
        password: formData.get('password'),
        roles: Array.from(formData.getAll('roles')).map(roleName => ({
            name: roleName
        }))
    };

    fetch('/api/admin/newUser', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(userData)
    })
        .then(response => response.json())
        .then(() => {
            return fetch('/api/admin');
        })
        .then(response => response.json())
        .then(users => {
            updateUsersTable(users);
            form.reset();
            $('#addUserModal').modal('hide');
            $('#users-tab').tab('show');
        });
}
