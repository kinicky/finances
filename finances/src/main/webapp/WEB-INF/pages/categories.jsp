<%@include file="header.jsp" %>

    <table>
        <tr>
            <td colspan="5">
                <c:if test="${empty txns}">
                    <br />
                    <br />
                    <p>No Transactions found</p>
                </c:if> 
                <c:if test="${not empty txns}">
                    <br />
                    <br />
                    ${fn:length(txns)} transactions found:
                    <div class="prettyTable">
                    <table>
                        <tr>
                            <td>Date</td>
                            <td>Description</td>
                            <td>Withdrawal</td>
                            <td>Deposit</td>
                            <td>Balance</td>
                            <td>Category</td>
                            <td></td>
                        </tr>
                        <c:forEach items="${txns}" var="txn" varStatus="i">
                            <tr>
                                <td>${fn:escapeXml(txn.date)}</td>
                                <td>${fn:escapeXml(txn.description)}</td>
                                <td style="text-align: right;">${fn:escapeXml(txn.withdrawal)}</td>
                                <td style="text-align: right;">${fn:escapeXml(txn.deposit)}</td>
                                <td style="text-align: right;">${fn:escapeXml(txn.balance)}</td>
                                <td>${fn:escapeXml(txn.category)}</td>
                                <td style="text-align: right;">
                                <ul><li id="txn_${i.index}">Select
                                    <ul>
                                        <li>
                                            Education >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'School Supplies')">School Supplies</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Training Fees')">Training Fees</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Entertainment >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Books/Movies/Music')">Books/Movies/Music</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Movie theatre/Shows')">Movie theatre/Shows</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Physical/Athletic activities')">Physical/Athletic activities</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Vacations')">Vacations</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Financial Services >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Credit card fees')">Credit card fees</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Service charges')">Service charges</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Food >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Alcoholic beverages')">Alcoholic beverages</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Delivery')">Delivery</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Fast Food')">Fast Food</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Groceries')">Groceries</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Restaurants')">Restaurants</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Health Care >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Dental care')">Dental care</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Medical care')">Medical care</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Pharmacy/Medication')">Pharmacy/Medication</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Housing >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Electricity/Heating')">Electricity/Heating</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Furnishings and decoration')">Furnishings and decoration</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Maintenance and renovations')">Maintenance and renovations</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Municipal/School taxes')">Municipal/School taxes</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Rent/Mortgage')">Rent/Mortgage</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Income >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Government benefits')">Government benefits</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Investments/Interest')">Investments/Interest</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Salary')">Salary</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Retail Stores >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Appliances')">Appliances</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Clothing')">Clothing</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Electronics/Computer equipment')">Electronics/Computer equipment</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Savings >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Emergency Fund')">Emergency Fund</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'RESP')">RESP</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'RRSP')">RRSP</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'TFSA')">TFSA</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Telecom Services >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Internet')">Internet</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Phone')">Phone</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Television')">Television</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Transportation >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Public transporation')">Public transporation</li>
                                            </ul>
                                        </li>
                                        <li>
                                            Withdrawals >
                                            <ul>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Cheques/Cash')">Cheques/Cash</li>
                                                <li onclick="selectCategory(${i.index}, ${txn.id}, 'Transfers')">Transfers</li>
                                            </ul>
                                        </li>
                                    </ul>
                                    </li>
                                    </ul>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    </div>
                </c:if>
            </td>
        </tr>
        </table>
        
<%@include file="footer.jsp" %>