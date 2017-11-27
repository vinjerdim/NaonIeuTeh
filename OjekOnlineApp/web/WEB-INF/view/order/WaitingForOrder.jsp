<div class="container">
    <div class="edit-profile-header">
        Looking For An Order   
    </div>
    <p class="looking-for-order">Finding Order....</p>
    <input class ="red-button clickable-button" id="kirim-button"
           type="submit" value="CANCEL" onclick="cancelOrder(<%=id%>)">
</div>
<script>
    function cancelOrder(id) {
        $.get("http://localhost:3000/findorder/cancel?accountid=" + id,
                function (data, status) {
                    window.location.href = "FindingOrder";
                });
    }
</script>