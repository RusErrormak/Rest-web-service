
document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/user/info')
        .then(response => response.json())
        .then(user => {
            const userTableBody = document.querySelector('#userTableBody');
            if (userTableBody) {
                userTableBody.innerHTML = `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map(role => role.name).join(', ')}</td>
                    </tr>
                `;
            }

            const navbarBrand = document.querySelector('.navbar-brand');
            if (navbarBrand) {
                navbarBrand.innerHTML = `${user.email} with roles: 
                                 ${user.roles.map(role => role.name).join(', ')}`;
            }

            const adminMenu = document.getElementById('adminMenu');
            if (adminMenu) {
                adminMenu.style.display = user.roles.some(role => role.name === 'ROLE_ADMIN') ? 'block' : 'none';
            }
        })

});
