<div class="container">
    <div class="edit-profile-header">
        Looking For An Order   
    </div>
    <input class ="green-button clickable-button" id="kirim-button"
           type="button" value="FIND ORDER" onclick="findOrder(<%=id%>)">
</div>
<script>
    function findOrder(id) {
        $.get("http://localhost:3000/findorder/find?accountid=" + id,
                function (data, status) {
                    window.location.href = "WaitingForOrder";
                });
    }
</script>