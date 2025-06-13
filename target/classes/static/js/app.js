function showNotification(message, isError = false) {
    const notification = document.getElementById('notification');
    notification.textContent = message;
    notification.classList.remove('hidden', 'bg-green-500', 'bg-red-500');
    notification.classList.add(isError ? 'bg-red-500' : 'bg-green-500');
    setTimeout(() => notification.classList.add('hidden'), 3000);
}

function validateLoginForm() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    let isValid = true;

    if (!username) {
        document.getElementById('usernameError').classList.remove('hidden');
        isValid = false;
    } else {
        document.getElementById('usernameError').classList.add('hidden');
    }

    if (!password) {
        document.getElementById('passwordError').classList.remove('hidden');
        isValid = false;
    } else {
        document.getElementById('passwordError').classList.add('hidden');
    }

    return isValid;
}

async function validateRegisterForm(event) {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    let isValid = true;

    if (username.length < 3) {
        document.getElementById('usernameError').classList.remove('hidden');
        isValid = false;
    } else {
        document.getElementById('usernameError').classList.add('hidden');
    }

    if (password.length < 8) {
        document.getElementById('passwordError').classList.remove('hidden');
        isValid = false;
    } else {
        document.getElementById('passwordError').classList.add('hidden');
    }

    if (isValid) {
        try {
            const response = await fetch('/api/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`
            });
            if (response.ok) {
                showNotification('Registration successful! Redirecting to login...');
                setTimeout(() => window.location.href = '/login', 2000);
            } else {
                const error = await response.text();
                showNotification(error, true);
            }
        } catch (error) {
            showNotification('Registration failed: ' + error.message, true);
        }
    }
    return false;
}

function showBookingModal(roomId, roomNumber) {
    document.getElementById('bookingModal').classList.remove('hidden');
    document.getElementById('modalTitle').textContent = `Book Room ${roomNumber}`;
    document.getElementById('roomId').value = roomId;
}

function closeBookingModal() {
    document.getElementById('bookingModal').classList.add('hidden');
    document.getElementById('fromDateError').classList.add('hidden');
    document.getElementById('toDateError').classList.add('hidden');
}

async function submitBooking(event) {
    event.preventDefault();
    const roomId = document.getElementById('roomId').value;
    const fromDate = document.getElementById('fromDate').value;
    const toDate = document.getElementById('toDate').value;
    let isValid = true;

    const today = new Date().toISOString().split('T')[0];
    if (fromDate < today) {
        document.getElementById('fromDateError').classList.remove('hidden');
        isValid = false;
    } else {
        document.getElementById('fromDateError').classList.add('hidden');
    }

    if (toDate <= fromDate) {
        document.getElementById('toDateError').classList.remove('hidden');
        isValid = false;
    } else {
        document.getElementById('toDateError').classList.add('hidden');
    }

    if (isValid) {
        try {
            const response = await fetch('/bookings', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ room: { id: roomId }, fromDate, toDate })
            });
            if (response.ok) {
                showNotification('Booking successful!');
                closeBookingModal();
                setTimeout(() => window.location.href = '/ui/bookings', 2000);
            } else {
                const error = await response.text();
                showNotification('Booking failed: ' + error, true);
            }
        } catch (error) {
            showNotification('Booking failed: ' + error.message, true);
        }
    }
    return false;
}

document.getElementById('hotelFilter')?.addEventListener('input', function() {
    const filter = this.value.toLowerCase();
    document.querySelectorAll('.hotel-card').forEach(card => {
        const name = card.querySelector('h2').textContent.toLowerCase();
        card.style.display = name.includes(filter) ? '' : 'none';
    });
});