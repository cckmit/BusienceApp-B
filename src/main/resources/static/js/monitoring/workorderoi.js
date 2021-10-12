$("#modalbtn").click(function() {
	$("#myModal").modal();
});

var workorder_tbl = new Tabulator("#workorder_tbl", {
	layout: "fitColumns",
	columns: [
		{ title: "작업지시번호", field: "ono", headerSort: false, headerHozAlign: "center" },
		{ title: "제품명", field: "equipname", headerSort: false, headerHozAlign: "center" },
		{ title: "지시수량", field: "orderqty", headerSort: false, formatter: "money", hozAlign: "right", formatterParams: { precision: false }, headerHozAlign: "center" },
		{ title: "생산수량", field: "productqty", headerSort: false, formatter: "money", hozAlign: "right", formatterParams: { precision: false }, headerHozAlign: "center" },
		{ title: "작업상태", field: "productstat", headerSort: false, editor: "select", editorParams: { values: { "접수완료": "접수완료", "작업시작": "작업시작", "작업완료": "작업완료" } }, headerHozAlign: "center" },
	]
});

var tabledata = [
	{ 'ono': '20210713-A01044-01', 'equipname': '제오닉 밀폐 XE011', 'orderqty': '300000', 'productqty': '4000000', 'productstat': '접수완료' }
];
workorder_tbl.setData(tabledata);

// -------------------------------------------------------------------------------------------------------

var production_mgmt_tbl = new Tabulator("#production_mgmt_tbl", {
	width: "80%",
	height: "85%",
	layout: "fitColumns",
	columns: [
		{ title: "작업지시번호", field: "ono", headerSort: false, headerHozAlign: "center" },
		{ title: "제품명", field: "equipname", headerSort: false, headerHozAlign: "center" },
		{ title: "지시수량", field: "orderqty", headerSort: false, formatter: "money", hozAlign: "right", formatterParams: { precision: false }, headerHozAlign: "center" },
		{ title: "생산수량", field: "productqty", headerSort: false, formatter: "money", hozAlign: "right", formatterParams: { precision: false }, headerHozAlign: "center" },
		{ title: "누적수량", field: "eproductqty", headerSort: false, formatter: "money", hozAlign: "center", formatterParams: { precision: false }, headerHozAlign: "center" },
	]
});

var i = 0;
var result = 0;
setInterval(function request() {
	result += i;
	i++;
	tabledata2 = [
		{ 'ono': '20210713-A01044-01', 'equipname': '제오닉 밀폐 XE011', 'orderqty': '300000', 'productqty': i, 'eproductqty': result }
	];

	//production_mgmt_tbl.addData(tabledata2);

	production_mgmt_tbl.setSort([
		{ column: "productqty", dir: "desc" } //sort by this first
	]);
}, 1000);

var getCookie = function(name) {
	var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
	return value ? value[2] : null;
};

window.onload = function() {
	// getCookie(변수이름)
	var is_expend = getCookie("expend");
	console.log("쿠키 is_expend변수에 저장된 값: " + is_expend);
}