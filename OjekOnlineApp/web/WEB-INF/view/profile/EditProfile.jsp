<div class="edit-profile-container">
    <form action="EditProfileAfter" method="post" name="editprofileform">
        <h1 class="edit-profile-header">Edit Profile Information</h1>
            <img id="profile-pic" src="" alt="Gambar">
            <div class="img-chooser">
            <p>Update profile picture</p>
            <input type="file" name="image" accept="image/jpeg, image/png">
        </div>
        <div class="edit-profile-btm">
            <div class="form-editprofile">
                <label for="your-name">Your Name</label>
                <jsp:include page="/ThinProfile?choice=1"/>
            </div>
            <div class="form-editprofile">
                <label for="phone-number">Phone</label>
                <jsp:include page="/ThinProfile?choice=2"/>
            </div>
            <div class="button-form">
                <div>
                    Status driver
                </div>
                <label class="switch">
                    <jsp:include page="/ThinProfile?choice=3"/>
                    <span class="slider round"></span>
                </label>
            </div>
            <input class="cancel-button" value="Back" onclick="window.location.href='Profile'"> 
            <input class="accept-button" id="save-profile" type="submit" value="Save">
        </div>
    </form>
</div>