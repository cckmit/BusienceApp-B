//tabulator custom key binding
Tabulator.prototype.extendModule("keybindings", "actions", {
	//위 방향키 눌렀을때
	"navUp": function (e) {
		e.preventDefault(); // prevent scroll

		var selectedRow = this.table.getSelectedRows()[0];
		if (!selectedRow){
			return;
		}
		
		var prevRow = selectedRow.getPrevRow();

		if (!prevRow){
			return;
		}

		this.table.deselectRow();
		this.table.selectRow(prevRow);
		this.table.scrollToRow(prevRow, "top", false)
	},
	//아래방향키 눌렀을떄
	"navDown": function (e) {
		e.preventDefault(); // prevent scroll

		var selectedRow = this.table.getSelectedRows()[0];
		if (!selectedRow){
			return;
		}
		
		var nextRow = selectedRow.getNextRow();

		if (!nextRow){
			return;
		}

		this.table.deselectRow();
		this.table.selectRow(nextRow);
		this.table.scrollToRow(nextRow, "bottom", false)
	},
});