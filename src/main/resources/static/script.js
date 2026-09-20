const API_URL = "/applications";

const applicationForm = document.getElementById("applicationForm");
const applicationsList = document.getElementById("applicationsList");

const applicationIdInput = document.getElementById("applicationId");
const submitButton = document.getElementById("submitButton");
const formTitle = document.getElementById("formTitle");


// ===============================
// GET ALL APPLICATIONS
// ===============================

async function loadApplications() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Failed to load applications");
        }

        const applications = await response.json();

        displayApplications(applications);

    } catch (error) {

        applicationsList.innerHTML =
            "<p>Failed to load applications.</p>";

        console.error(error);
    }
}


// ===============================
// DISPLAY APPLICATIONS
// ===============================

function displayApplications(applications) {

    if (applications.length === 0) {

        applicationsList.innerHTML =
            "<p>No applications added yet.</p>";

        return;
    }

    applicationsList.innerHTML = "";

    applications.forEach(application => {

        const card = document.createElement("div");

        card.className = "application-card";

        card.innerHTML = `
            <h3>${application.companyName}</h3>

            <p>
                <strong>Role:</strong>
                ${application.role}
            </p>

            <p>
                <strong>Status:</strong>
                ${application.status}
            </p>

            <p>
                <strong>Date:</strong>
                ${application.applicationDate}
            </p>

            <p>
                <strong>Location:</strong>
                ${application.location}
            </p>

            <button
                class="edit-btn"
                onclick="editApplication(${application.id})">
                Edit
            </button>

            <button
                class="delete-btn"
                onclick="deleteApplication(${application.id})">
                Delete
            </button>
        `;

        applicationsList.appendChild(card);
    });
}


// ===============================
// ADD / UPDATE APPLICATION
// ===============================

applicationForm.addEventListener("submit", async function(event) {

    event.preventDefault();

    const id = applicationIdInput.value;

    const application = {

        companyName:
            document.getElementById("companyName").value,

        role:
            document.getElementById("role").value,

        status:
            document.getElementById("status").value,

        applicationDate:
            document.getElementById("applicationDate").value,

        location:
            document.getElementById("location").value
    };


    try {

        let response;

        // UPDATE
        if (id) {

            response = await fetch(
                `${API_URL}/${id}`,
                {
                    method: "PUT",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(application)
                }
            );

        }

        // CREATE
        else {

            response = await fetch(
                API_URL,
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(application)
                }
            );
        }


        if (!response.ok) {
            throw new Error("Failed to save application");
        }


        // Reset form

        applicationForm.reset();

        applicationIdInput.value = "";

        submitButton.textContent = "Add Application";

        formTitle.textContent = "Add Application";


        // Reload applications

        await loadApplications();


    } catch (error) {

        console.error(error);

        alert("Failed to save application.");
    }

});


// ===============================
// EDIT APPLICATION
// ===============================

async function editApplication(id) {

    try {

        const response = await fetch(
            `${API_URL}/${id}`
        );

        if (!response.ok) {
            throw new Error("Failed to get application");
        }

        const application = await response.json();


        // Fill form

        document.getElementById("companyName").value =
            application.companyName;

        document.getElementById("role").value =
            application.role;

        document.getElementById("status").value =
            application.status;

        document.getElementById("applicationDate").value =
            application.applicationDate;

        document.getElementById("location").value =
            application.location;


        // Store ID

        applicationIdInput.value =
            application.id;


        // Change form UI

        submitButton.textContent =
            "Update Application";

        formTitle.textContent =
            "Edit Application";


        // Scroll to form

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });


    } catch (error) {

        console.error(error);

        alert("Failed to load application.");
    }
}


// ===============================
// DELETE APPLICATION
// ===============================

async function deleteApplication(id) {

    const confirmed =
        confirm("Are you sure you want to delete this application?");

    if (!confirmed) {
        return;
    }


    try {

        const response = await fetch(
            `${API_URL}/${id}`,
            {
                method: "DELETE"
            }
        );


        if (!response.ok) {
            throw new Error("Failed to delete application");
        }


        await loadApplications();


    } catch (error) {

        console.error(error);

        alert("Failed to delete application.");
    }
}


// ===============================
// LOAD DATA WHEN PAGE OPENS
// ===============================

loadApplications();
