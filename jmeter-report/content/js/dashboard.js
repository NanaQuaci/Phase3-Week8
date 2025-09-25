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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 1800, 675, 37.5, 26.807222222222233, 8, 597, 28.0, 40.0, 43.0, 81.0, 89.8069151324652, 668.0689739715862, 18.340694460035923], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Open Product Page", 225, 0, 0.0, 15.648888888888887, 8, 46, 11.0, 38.0, 40.0, 44.74000000000001, 11.659239299409265, 115.92684475075139, 2.231651272152555], "isController": false}, {"data": ["Submit Order", 225, 225, 100.0, 37.00444444444443, 33, 78, 36.0, 39.0, 40.0, 44.48000000000002, 11.68770453482936, 5.626990562178587, 2.899098585787751], "isController": false}, {"data": ["Homepage", 225, 0, 0.0, 49.137777777777764, 8, 597, 39.0, 72.4, 88.69999999999999, 591.74, 11.24381590125431, 143.4443477962121, 1.9764520138923594], "isController": true}, {"data": ["CartPage", 225, 0, 0.0, 12.951111111111109, 8, 47, 11.0, 16.0, 36.69999999999999, 44.22000000000003, 11.67618059159315, 129.74410717760767, 2.155076300596783], "isController": false}, {"data": ["Checkout", 225, 225, 100.0, 74.20444444444442, 68, 144, 73.0, 77.0, 79.0, 86.70000000000005, 11.664679350925397, 11.265984255923064, 5.091905927601223], "isController": true}, {"data": ["Open Checkout Page", 225, 225, 100.0, 37.2, 33, 66, 37.0, 40.0, 41.0, 50.180000000000064, 11.68709744442136, 5.660937824641596, 2.2027439519270726], "isController": false}, {"data": ["Send Message", 225, 225, 100.0, 37.17777777777778, 34, 83, 37.0, 40.0, 40.0, 46.74000000000001, 11.693171188026191, 5.664082800644423, 3.2316088341908324], "isController": false}, {"data": ["View Cart", 225, 0, 0.0, 12.617777777777777, 8, 43, 11.0, 15.0, 36.69999999999999, 42.74000000000001, 11.694994542335879, 146.11398274663443, 2.1585487973647277], "isController": false}, {"data": ["Open Contact Page", 225, 0, 0.0, 12.72000000000001, 9, 40, 11.0, 16.0, 36.69999999999999, 39.74000000000001, 11.706555671175858, 137.6135088774714, 2.1606826385275757], "isController": false}, {"data": ["Open Homepage", 225, 0, 0.0, 49.137777777777764, 8, 597, 39.0, 72.4, 88.69999999999999, 591.74, 11.312785962089597, 144.32424172909649, 1.988575657398562], "isController": false}, {"data": ["Cart", 225, 0, 0.0, 41.21777777777779, 27, 130, 34.0, 62.400000000000006, 88.69999999999999, 123.44000000000005, 11.647168443938295, 390.74501032068537, 6.528783873848224], "isController": true}, {"data": ["Contact", 225, 225, 100.0, 49.8977777777778, 44, 97, 48.0, 54.0, 73.69999999999999, 78.48000000000002, 11.68406293815236, 143.00877197252944, 5.385622760554603], "isController": true}]}, function(index, item){
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
