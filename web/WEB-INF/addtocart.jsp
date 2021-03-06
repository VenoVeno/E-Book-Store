
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>E-Book Shop</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/newcss.css" />
    </head>
    <body>
        <div class="header">
            <h1>E-Book</h1>
            <h6 onclick="showCart()" class="cartText">Cart: <span id="cartCt">0</span> item(s)</h6>
        </div>
        
        <div id="cartBox" class="cartBox">
        </div>
        
        <div class="container">
            <h3>Books</h3>
            <table>
                <tr>
                    <th>Product id</th>
<!--                    <th></th>-->
                    <th>Author</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Category</th>
                    <th>Price</th>
                </tr>
                <%
                    ResultSet rs = (ResultSet) request.getAttribute("books");
                    while(rs.next()) {
                 %>
                    <tr>
                        <td onclick="showDetails(<%=rs.getString("id")%>)"><%=rs.getString("id")%></td>
                        <td onclick="showDetails(<%=rs.getString("id")%>)"><%=rs.getString("author")%></td>
                        <td onclick="showDetails(<%=rs.getString("id")%>)"><%=rs.getString("title")%></td>
                        <td onclick="showDetails(<%=rs.getString("id")%>)"><%=rs.getString("description")%></td>
                        <td onclick="showDetails(<%=rs.getString("id")%>)"><%=rs.getString("category")%></td>
                        <td onclick="showDetails(<%=rs.getString("id")%>)"><%=rs.getString("price")%></td>

                        <td>
                            <button onclick="addToCart(<%=rs.getString("id")%>)" class="addBtn" id="<%=rs.getString("id")%>">Add to Cart</button>
                        </td>
                    </tr>
                <%
                    }
                %>
            </table>
        </div>
            
        <div class="footer">
            © E-Book Inc., 2020
        </div>
        <script type="text/javascript">
            var pObj =  {};
            var cart = {};
            var str = "";
            var cartBox = document.getElementById("cartBox");
            
            cart["total"] = 0;
            cart["cost"] = 0;
            cart["items"] = {};
                
            
            <%
                rs.last();
            %>
            <%
                rs.beforeFirst();
                while(rs.next()) {
            %>
                pObj["<%=rs.getString("id")%>"] = {
                    "id":"<%=rs.getString("id")%>",
                    "author":"<%=rs.getString("author")%>",
                    "title":"<%=rs.getString("title")%>",
                    "description":"<%=rs.getString("description")%>",
                    "category":"<%=rs.getString("category")%>",
                    "price":"<%=rs.getString("price")%>"
                }
                console.log(pObj);
            <%
                }
            %>               
            function updateCartString() 
            {
                str = "";
                str += "<h5>Cart Details</h5><table>";
                for(var i in cart["items"]) {
                    str += "<tr><td>" + pObj[i]["id"] + "</td><td>" + cart["items"][i] + "</td><td>" + "₹" + parseInt(cart["items"][i]) * parseInt(pObj[i]["Price"]) + "</td></tr>";
                }
                str += "</table><br>TOTAL = ₹" + cart["cost"];
                
                cartBox.innerHTML = str;
            }
    
            function showDetails(id) {
                alert(pObj[id]["id"] + "\n\n by " + pObj[id]["author"] + "\n\n Wrote" + pObj[id]["title"] + "\n\nUnder" + pObj[id]["category"]);
            }
            
            function showCart() {
                if(cartBox.style.display == "block") {
                    cartBox.style.display = "none";
                    return;
                }
                cartBox.innerHTML = str;
                cartBox.style.display = "block";
            }
            
            function addToCart(id) {
                var cartCt = document.getElementById("cartCt");
                
                cart["total"] += 1;
                cart["cost"] += parseInt(pObj[id]["price"]);
                if(cart["items"][id]) {
                    cart["items"][id] += 1;
                } else {
                    cart["items"][id] = 1;
                }
                
                cartCt.innerHTML = cart["total"];
                
                alert("Added: '" + pObj[id]["id"] + "' to cart successfully!");
                updateCartString();
                console.log(cart);
            }
           
            window.onload = function() {                
            }
        </script>    
    </body>
    
</html>
