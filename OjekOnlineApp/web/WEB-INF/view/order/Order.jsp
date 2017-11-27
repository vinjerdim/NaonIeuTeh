<div class="container">
    <div class="edit-profile-header">
        Make an order       
    </div>
    <div class="progress-container">
        <div class="progress selected">
            <div class="progress-num">1</div> Select Destination
        </div>
        <div class="progress">
            <div class="progress-num">2</div> Select a driver
        </div>
        <div class="progress">
            <div class="progress-num">3</div> Chat Driver
        </div>
        <div class="progress">
            <div class="progress-num">4</div> Complete Your Order
        </div>
    </div>
    <form action="SelectDriver"  method='post'>
        <div class="edit-profile-btm">
            <div class="form-input">
                <label for="pick-point">Picking point</label>
                <input class="ep-textarea" type="text" name="pick-point" />
            </div>
            <div class="form-input">
                <label for="destination">Destination</label>
                <input class="ep-textarea" type="text" name="destination" />
            </div>
            <div class="form-input">
                <label for="pref-driver">Preferred Driver</label>
                <input class="ep-textarea" type="text" name="pref-driver" placeholder="(Optional)"/>
            </div>
            <div class="right-align">
                <input class="accept-button select-driver-btn completeorder-btn" type="submit" value="Next" />
            </div>
        </div>
    </form>
</div>