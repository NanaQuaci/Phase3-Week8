/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 62.5, "KoPercent": 37.5};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.5822222222222222, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "Open Product Page"], "isController": false}, {"data": [0.0, 500, 1500, "Submit Order"], "isController": false}, {"data": [0.9933333333333333, 500, 1500, "Homepage"], "isController": true}, {"data": [1.0, 500, 1500, "CartPage"], "isController": false}, {"data": [0.0, 500, 1500, "Checkout"], "isController": true}, {"data": [0.0, 500, 1500, "Open Checkout Page"], "isController": false}, {"data": [0.0, 500, 1500, "Send Message"], "isController": false}, {"data": [1.0, 500, 1500, "View Cart"], "isController": false}, {"data": [1.0, 500, 1500, "Open Contact Page"], "isController": false}, {"data": [0.9933333333333333, 500, 1500, "Open Homepage"], "isController": false}, {"data": [1.0, 500, 1500, "Cart"], "isController": true}, {"data": [0.0, 500, 1500, "Contact"], "isController": true}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 1800, 675, 37.5, 26.93666666666664, 14, 530, 21.0, 35.0, 60.0, 81.99000000000001, 89.64589870013447, 703.2963933089297, 18.307811099282834], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Open Product Page", 225, 0, 0.0, 21.44000000000001, 15, 45, 20.0, 30.0, 33.0, 42.74000000000001, 11.580626897936074, 138.35546854894747, 2.216604367183077], "isController": false}, {"data": ["Submit Order", 225, 225, 100.0, 23.791111111111107, 16, 61, 21.0, 31.0, 32.0, 54.76000000000022, 11.585993820803296, 5.578022415679711, 2.8738695610195673], "isController": false}, {"data": ["Homepage", 225, 0, 0.0, 57.399999999999956, 14, 530, 54.0, 77.0, 101.39999999999998, 525.22, 11.21746933891714, 94.2788861520341, 1.9718207822315286], "isController": true}, {"data": ["CartPage", 225, 0, 0.0, 21.635555555555563, 14, 56, 20.0, 28.0, 33.0, 42.92000000000007, 11.583011583011583, 162.52925916988417, 2.1378800675675675], "isController": false}, {"data": ["Checkout", 225, 225, 100.0, 47.69777777777775, 33, 83, 41.0, 63.0, 65.0, 76.44000000000005, 11.574669478882658, 11.179050893178662, 5.052614508848192], "isController": true}, {"data": ["Open Checkout Page", 225, 225, 100.0, 23.906666666666673, 16, 41, 21.0, 32.0, 33.69999999999999, 37.74000000000001, 11.585993820803296, 5.611965756951596, 2.183688288491246], "isController": false}, {"data": ["Send Message", 225, 225, 100.0, 24.453333333333322, 16, 141, 21.0, 32.0, 34.0, 50.74000000000001, 11.593157460840889, 5.615435645094806, 3.203968321697238], "isController": false}, {"data": ["View Cart", 225, 0, 0.0, 20.68888888888889, 14, 50, 20.0, 26.0, 31.69999999999999, 43.700000000000045, 11.584204293878392, 154.02909083303302, 2.138100206584977], "isController": false}, {"data": ["Open Contact Page", 225, 0, 0.0, 22.177777777777777, 14, 308, 20.0, 26.0, 31.0, 68.90000000000032, 11.587783900705567, 157.96478078166038, 2.1387608957356954], "isController": false}, {"data": ["Open Homepage", 225, 0, 0.0, 57.399999999999956, 14, 530, 54.0, 77.0, 101.39999999999998, 525.22, 11.286681715575622, 94.86059184537247, 1.9839870203160272], "isController": false}, {"data": ["Cart", 225, 0, 0.0, 63.76444444444445, 45, 140, 61.0, 76.0, 82.69999999999999, 109.70000000000005, 11.553866694053609, 453.7817751392883, 6.476483869518332], "isController": true}, {"data": ["Contact", 225, 225, 100.0, 46.6311111111111, 31, 449, 42.0, 56.0, 62.69999999999999, 92.06000000000017, 11.57764742204384, 163.43452317780694, 5.336571858598332], "isController": true}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["404/Not Found", 675, 100.0, 37.5], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 1800, 675, "404/Not Found", 675, "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["Submit Order", 225, 225, "404/Not Found", 225, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Open Checkout Page", 225, 225, "404/Not Found", 225, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["Send Message", 225, 225, "404/Not Found", 225, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
