<div class="container">
    <div class="edit-profile-header">
        Looking For An Order   
    </div>
    <p class="looking-for-order">Finding Order....</p>
    <input class ="red-button clickable-button" id="kirim-button"
           type="submit" value="CANCEL" onclick="cancelOrder(<%=id%>)">
</div>
<script>
    messagingApp.onMessage(function (payload) {
        console.log("WaitingOrder.jsp : got notif > ", payload);
        window.location.href = "GotAnOrder?passid=" + payload.data.id + 
                "&passname=" + payload.data.name;
    });
    
    function cancelOrder(id) {
        $.get("http://localhost:3000/findorder/cancel?accountid=" + id, function (data, status) {
            console.log(data);
            console.log(status);
            window.location.href = "FindingOrder";
        });
    }
</script>