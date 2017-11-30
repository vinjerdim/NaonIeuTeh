<div class="edit-profile-header">
    Make an order       
</div>
<div class="progress-container">
    <div class="progress">
        <div class="progress-num">1</div> Select Destination
    </div>
    <div class="progress">
        <div class="progress-num">2</div> Select a driver
    </div>
    <div class="progress">
        <div class="progress-num">3</div> Chat Driver
    </div>
    <div class="progress selected">
        <div class="progress-num">4</div> Complete Your Order
    </div>
</div>
<div class="edit-profile-header">
    How was it?
</div>
<form action="AddTransaction" method="POST">
    <div class="completeorder-container">
        <jsp:include page="/ShowCompleteOrder"/>
        <span>
            <input id="rating5" type="radio" name="rating" value="5">
            <label for="rating5">5</label>
            <input id="rating4" type="radio" name="rating" value="4">
            <label for="rating4">4</label>
            <input id="rating3" type="radio" name="rating" value="3">
            <label for="rating3">3</label>
            <input id="rating2" type="radio" name="rating" value="2">
            <label for="rating2">2</label>
            <input id="rating1" type="radio" name="rating" value="1" checked="true">
            <label for="rating1">1</label>
        </span>
        <textarea cols="5" placeholder="Your comment..." name="comment"></textarea>
    </div>
    <div class="right-align">
        <input class="accept-button select-driver-btn completeorder-btn" type="submit" value="Complete">
    </div>
</form>