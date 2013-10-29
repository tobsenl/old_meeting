	/** 和Globa.js配合使用，空值判断
	 *
	 **/
	function value_change(form){
		if(arrays['field_types'][this.form.fields.selectedIndex] == 'date'){
			form.image1.style.display = 'block'
			form.mvalue.readOnly = true
			form.mvalue.onChange = "return isNotEmpty(document.form.mvalue.value)"
		}else if(arrays['field_types'][this.form.fields.selectedIndex] == 'number'){ 
			form.mvalue.readOnly = false
			form.image1.style.display = 'none'
			form.mvalue.onChange = "return isNumber(document.form.mvalue.value)"
		}else if(arrays['field_types'][this.form.fields.selectedIndex] == 'integer'){ 
			form.mvalue.readOnly = false
			form.image1.style.display = 'none'
			form.mvalue.onChange = "return isInteger(document.form.mvalue.value)"
		}else{
			form.mvalue.readOnly = false
			form.image1.style.display = 'none'
			form.mvalue.onChange = "return true"
		}
	}
	// 生成复杂查询语句的where条件
	function createSQL(form){
		if(isEmpty(form.mvalue.value))
			return;
		var show = ""
		if (form.SHOW.value != "") {
			if (form.Logic[0].checked) {
		      		show = "\n并且 "
		    	} else {
			      	show = "\n或者 "
		    	}
		}
		show = show + form.fields.options[form.fields.selectedIndex].text + " " 
		show = show + form.compare.options[form.compare.selectedIndex].text + " "
		show = show + form.mvalue.value + ";"
		form.SHOW.value = form.SHOW.value + show
		//生成sql语句
		var sql = ""
  		if (form.SQL.value != "") {
    			if (form.Logic[0].checked) {
      				sql = " and "
    			} else {
      				sql = " or "
    			}
  		}
  		var selectedFieldName = form.fields.options[form.fields.selectedIndex].value
		var selectedCompare   = form.compare.options[form.compare.selectedIndex].value
		
		//检查并替换条件值中的单引号，保证SQL语句的正确性
		var intStart = 0
		var strSQLValue = ""
		for (i = 0; i < form.mvalue.value.length; i++) {
			if (form.mvalue.value.charAt(i) == "'") {
				strSQLValue = strSQLValue + form.mvalue.value.substring(intStart, i) + "''"
				intStart = i + 1
				continue
			}
		}
		strSQLValue = strSQLValue + form.mvalue.value.substr(intStart)
		
		if (selectedCompare == " like ") {
			var stringHead = "'%"
			var stringTail = "%'"
		} else {
			var stringHead = "'"
			var stringTail = "'"
		}
		if (arrays['field_types'][this.form.fields.selectedIndex] == 'date') {
			var dateHead = "TO_DATE('"
			var dateTail = "','YYYY-MM-DD HH24:MI:SS')"			
			strSQLValue = strSQLValue + " 00:00:00";
			
			if(selectedCompare==" = "){     
				
				sql = sql + selectedFieldName +" > TO_DATE('" +strSQLValue+"','YYYY-MM-DD HH24:MI:SS') AND "+selectedFieldName+" < TO_DATE('" +strSQLValue+"','YYYY-MM-DD HH24:MI:SS')+1"
				
			}else if(selectedCompare==" > "){
			
				sql = sql + selectedFieldName +" > TO_DATE('" +strSQLValue+"','YYYY-MM-DD HH24:MI:SS')+1"

			}else if(selectedCompare==" < "){
			
				sql = sql + selectedFieldName +" < TO_DATE('" +strSQLValue+"','YYYY-MM-DD HH24:MI:SS')"

			}else if(selectedCompare==" <= "){
			
				sql = sql + selectedFieldName +" < TO_DATE('" +strSQLValue+"','YYYY-MM-DD HH24:MI:SS')+1"
			}else if(selectedCompare==" >= "){
			
				sql = sql + selectedFieldName +" > TO_DATE('" +strSQLValue+"','YYYY-MM-DD HH24:MI:SS')"

			}
				


		} else{
		  	sql = sql + selectedFieldName + selectedCompare
    			sql = sql + stringHead + strSQLValue + stringTail
		}
		form.SQL.value = form.SQL.value + sql
		
  		form.mvalue.value = ""
	}
	
	// 生成简单查询的where条件
	function simpleSQL(){
		form.SQL.value = "";
		for (var n = 0; n < form.elements.length; n++) {
	    		var element = form.elements[n]
			for (var i = 0; i < document.form.fields.options.length; i++) {
		    		if(element.name == document.form.fields.options[i].value){
				    	if(element.value.length > 0){
					    	var sql = ""
				  		if (form.SQL.value != "")
				    			sql = " and "
				    		if(arrays['field_types'][i] == 'string')

				    			form.SQL.value = form.SQL.value + sql + element.name + " like '%" + sqlStr(element.value) + "%' ";
				    		else if(arrays['field_types'][i] == 'date')
				    			form.SQL.value = form.SQL.value + sql + " (" + element.name + " - " + "TO_DATE('" + element.value + "','YYYY-MM-DD')" + ") < 1 " + " and (" + element.name + " - " + "TO_DATE('" + element.value + "','YYYY-MM-DD')" + ") >= 0 ";
								
				    		else
				    			form.SQL.value = form.SQL.value + sql + element.name + " = " + element.name ;
				    	}
			    	}//if
		   	}//for
		}//for
		return true;
	}

 function checksub(form){
	if (form.SHOW.value=="")
	     	  {alert("请设置查询条件");
	    	   return false;
		     }
     else form.submit();
	}