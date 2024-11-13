document.addEventListener('DOMContentLoaded', function () {
    var rowslength = document.querySelectorAll('#fileTable tbody tr').length;
    document.getElementById('sum').textContent = rowslength
    document.getElementById('total').textContent = rowslength;
    new Tablesort(document.getElementById('fileTable'));
    document.getElementById('datatypeFilter').addEventListener('input', function () {
        var filter = this.value.toLowerCase();
        var rows = document.querySelectorAll('#fileTable tbody tr');
        var visibleRows = [];
        rows.forEach(function (row) {
            var datatype = row.querySelector('td:nth-child(2)').textContent.toLowerCase();
            var datatype1 = row.querySelector('td:nth-child(3)').textContent.toLowerCase();
            if (datatype.includes(filter) || datatype1.includes(filter)) {
                row.style.display = '';
                visibleRows.push(row);
            } else {
                row.style.display = 'none';
            }
        });
        visibleRows.forEach(function (row, index) {
            var idCell = row.querySelector('td:nth-child(1)');
            idCell.innerHTML = '<span id="sorted-id">' + (index + 1) + '</span>';
            var existingSpan = idCell.querySelector('#sorted-id');
            if (existingSpan) {
                existingSpan.textContent = index + 1;
            } else {
                var span = document.createElement('span');
                span.id = 'sorted-id';
                span.textContent = index + 1;
                idCell.appendChild(span);
            }
        });
        document.getElementById('sum').textContent = visibleRows.length;
        document.getElementById('total').textContent = rows.length;
    });
});