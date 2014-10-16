function isNumberKey(evt,number)
{
         var charCode = (evt.which) ? evt.which : event.keyCode;
 		if(charCode == 46 && number.indexOf(".") != -1){
			return false;
		}
         if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)){
            return false;
         }
         return true;
}
function nextIndex(tableId,row,desId){
	document.getElementById(tableId+':'+row+':'+desId).focus();
}
function confirmDailog(msg){
	if (!confirm(msg)) return false;
}

function confirmTransactionSave(msg,messageError,debit,credit){
	if (!confirm(msg)){
		return false;
	}else{
		alert('11111');
		alert(debit);
		alert(credit);
		if(debit!=credit){
			alert('22222');
			alert(messageError);
			return false;
		}else{
			alert('33333');
		}
	}
}