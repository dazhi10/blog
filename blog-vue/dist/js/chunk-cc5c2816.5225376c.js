(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-cc5c2816"],{"607a":function(e,t,n){},"71c2":function(e,t,n){"use strict";var a=function(){var e=this,t=e._self._c;return t("div",{},[t("div",{staticClass:"headBack"},[t("el-row",{staticClass:"container"},[t("el-col",{attrs:{span:24}},[t("div",{staticClass:"headBox"},[t("el-menu",{staticClass:"el-menu-demo",attrs:{"default-active":e.activeIndex,mode:"horizontal",router:!0},on:{select:e.handleSelect}},[t("el-menu-item",{attrs:{index:"/Home"}},[t("i",{staticClass:"fa fa-wa fa-home"}),e._v(" 首页")]),t("el-submenu",{attrs:{index:"/Share"}},[t("template",{slot:"title"},[t("i",{staticClass:"fa fa-wa fa-archive"}),e._v(" 分类")]),e._l(e.classListObj,(function(n,a){return t("el-menu-item",{key:"class1"+a,attrs:{index:"/Share?classId="+n.id}},[e._v(e._s(n.name))])}))],2),t("el-menu-item",{attrs:{index:"/Friendslink"}},[t("i",{staticClass:"fa fa-wa fa-users"}),e._v("友链")]),t("div",{staticClass:"userInfo"},[t("div",{directives:[{name:"show",rawName:"v-show",value:!e.haslogin,expression:"!haslogin"}],staticClass:"nologin"},[t("a",{attrs:{href:"javascript:void(0);"},on:{click:function(t){return e.logoinFun(1)}}},[e._v("登录 ")]),e._v("|"),t("a",{attrs:{href:"javascript:void(0);"},on:{click:function(t){return e.logoinFun(0)}}},[e._v(" 注册")])]),t("div",{directives:[{name:"show",rawName:"v-show",value:e.haslogin,expression:"haslogin"}],staticClass:"haslogin"},[t("el-avatar",{attrs:{size:38,src:e.headTou}}),t("ul",{staticClass:"haslogin-info"},[t("li",[t("a",{attrs:{href:"#/UserInfo"}},[e._v("个人中心")])]),t("li",[t("a",{attrs:{href:"javascript:void(0);"},on:{click:e.userlogout}},[e._v("退出登录")])])])],1)])],1)],1)])],1)],1),t("el-skeleton",{attrs:{rows:1,animated:"",loading:e.loading}},[t("template",{slot:"template"},[t("el-skeleton-item",{staticStyle:{width:"100%",height:"650px","margin-bottom":"100px"},attrs:{variant:"image"}})],1),t("div",{staticClass:"headImgBox",style:{backgroundImage:e.headBg}},[t("div",{staticClass:"scene"},[t("div",[t("span",{attrs:{id:"luke"}})])])])],2)],1)},r=[],s=(n("14d9"),n("c24f")),i=n("5f87"),o=n("b775");function c(){return Object(o["a"])({url:"/category/getCategoryList",headers:{isToken:!1},method:"get"})}n("8bbf");(function(e,t){function n(e){var t=this,n={autoplay:!0,erase:!0};t.events={},t.scene=-1,t.scenario=[],t.options=t.utils.merge(n,e||{}),t.casting={},t.current={},t.state="ready"}n.prototype={constructor:n,set:function(e,t){var n=this;switch(n.current.model=e,n.current.type){case"function":n.current.voice.apply(n,t);break;default:n.current.voice.innerHTML=e;break}return n},getSayingSpeed:function(e,t){"number"!==typeof e&&(t=e,e=0);var n=this,a=n.current.experience+e,r=t?a:n.utils.randomFloat(a,1);return n.utils.getPercentageBetween(1e3,50,r)},getInvincibility:function(){var e=this;return 10*e.current.experience},isMistaking:function(){var e=this;return e.current.experience<e.utils.randomFloat(0,1.4)},utils:{merge:function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n]);return e},getPercentageBetween:function(e,t,n){return e-e*n+t*n},randomChar:function(){var e=this,t="abcdefghijklmnopqrstuvwxyz";return t.charAt(e.randomNumber(0,t.length-1))},randomNumber:function(e,t){return Math.floor(Math.random()*(t-e+1))+e},randomFloat:function(e,t){return Math.round(10*(Math.random()*(t-e)+e))/10},hasClass:function(e,t){return e.classList?e.classList.contains(t):new RegExp("(^| )"+t+"( |$)","gi").test(e.className)},addClass:function(e,t){e.classList?e.classList.add(t):e.className+=" "+t},removeClass:function(e,t){e.classList?e.classList.remove(t):e.className=e.className.replace(new RegExp("(^|\\b)"+t.split(" ").join("|")+"(\\b|$)","gi")," ")}},train:function(e){var t=this,n={experience:.6,voice:function(e,t,n,a){console.log(e)},type:"function",model:""};return t.utils.merge(n,e)},describe:function(e,n,a){if("string"!==typeof e)throw"actor's name has wrong type: "+typeof e;var r=this,s={name:e};return void 0!==n&&(s.experience=n),void 0!==a&&(s.type="function"===typeof a?"function":"DOM","DOM"===s.type?s.voice="string"===typeof a?t.querySelector(a):a:s.voice=a),r.casting[e]=r.train(s),r},write:function(){for(var e,t=this,n=Array.prototype.splice.apply(arguments,[0]),a=0,r=n.length;a<r;a++)if(e=n[a],"string"===typeof e){var s=e.split(":"),i=s.length>1,o=i?s[0].trim():null,c=i?s[1]:s[0];i&&t.write({name:"actor",args:[o]}),t.options.erase&&i&&t.write({name:"erase"}),t.write({name:"say",args:[c,!i]})}else"number"===typeof e?e<0?t.write({name:"erase",args:[e]}):t.write({name:"wait",args:[e]}):"function"===typeof e?t.write({name:"call",args:[e]}):e instanceof Object&&t.scenario.push(e);return t.options.autoplay&&t.play(),t},play:function(e){var t=this;return!0===e&&(t.scene=-1),"ready"===t.state&&t.next(),t},on:function(e,t){var n=this;e=e.split(",");for(var a,r=0,s=e.length;r<s;r++)a=e[r]=e[r].trim(),(n.events[a]||(n.events[a]=[])).push(t);return n},emit:function(e,t,n){if("string"!==typeof e)throw"emit: scope missing";"string"!==typeof t?t=void 0:void 0!==t&&void 0===n&&(n=t);var a=this,r=e+(t?":"+t:"");return a.trigger(r,n).trigger("*",[r].concat(n)),a},trigger:function(e,t){var n=this,a=n.events[e]||[];t instanceof Array||(t=[t]);for(var r=0,s=a.length;r<s;r++)a[r].apply(n,[e].concat(t));return n},call:function(e,t){var n=this;return e.apply(n),t?n:n.next()},next:function(){var e=this,t=e.scenario[e.scene];if(t&&e.emit(t.name,"end",[t.name].concat(t.args)),e.scene+1>=e.scenario.length)e.state="ready";else{e.state="playing";var n=e.scenario[++e.scene];e.emit(n.name,"start",[n.name].concat(n.args)),e[n.name].apply(e,n.args)}return e},actor:function(e){var t=this;return t.current=t.casting[e],t.next()},say:function(e,t){var n,a,r=this,s=!1,i=r.getInvincibility();t?(a=r.current.model,n=r.current.model.length-1,e=a+e):(a=r.current.model="",n=-1);setTimeout((function t(){var o,c,u=a.charAt(n);s?(i=r.getInvincibility(),s=!1,o=null,c=a=a.substr(0,n),n--):(n++,o=--i<0&&r.isMistaking()?r.utils.randomChar():e.charAt(n),o!==e.charAt(n)&&(s=!0),c=a+=o),r.set(c,[c,o,u,e]),s||n<e.length?setTimeout(t,r.getSayingSpeed()):r.next()}),r.getSayingSpeed());return r},erase:function(e){var t=this,n="string"===typeof t.current.model?t.current.model.length:-1,a="number"===typeof e&&e<0?n+1+e:0;if(n<0)return t.next();setTimeout((function e(){var r=t.current.model.charAt(n),s=t.current.model.substr(0,--n);t.set(s,[s,null,r,s]),n>=a?setTimeout(e,t.getSayingSpeed(.2,!0)):t.next()}),t.getSayingSpeed(.2,!0));return t},wait:function(e){var t=this;return setTimeout((function(){t.next()}),e),t}},e.TheaterJS=n})(window,document);const u=(e,t)=>{var n=new TheaterJS;function a(){return self}n.describe("Luke",.9,t),n.on("*",(function(e,t,n,a){})).on("say:start, erase:start",(function(e){var t=this,n=t.current.voice;t.utils.addClass(n,"saying")})).on("say:end, erase:end",(function(e){var t=this,n=t.current.voice;t.utils.removeClass(n,"saying")})),n.write("Luke:Hello!",1500).write({name:"call",args:[a,!0]}).write((function(){n.play(!0)}))};var l={data(){return{userInfo:"",haslogin:!1,classListObj:"",activeIndex:"/",state:"",pMenu:!0,input:"",headBg:"url(http://rialetu26.hn-bkt.clouddn.com/headbg05.jpg)",headTou:"",projectList:"",loading:!0}},watch:{},methods:{getUserAvatar(){var e=this;localStorage.getItem("userInfo")&&(e.userInfo=JSON.parse(localStorage.getItem("userInfo")),e.userId=e.userInfo.id,Object(s["a"])(e.userId).then(t=>{e.headTou=t.avatar})),this.timeout=setTimeout(()=>{this.loading=!1},500)},handleOpen(e,t){},handleClose(e,t){},searchChangeFun(e){""==this.input&&(this.$store.state.keywords="",this.$router.push({path:"/"}))},getCategoryList(){c().then(e=>{this.classListObj=e})},handleSelect(e,t){},logoinFun:function(e){localStorage.setItem("logUrl",this.$route.fullPath),0==e?this.$router.push({path:"/Login?login=0"}):this.$router.push({path:"/Login?login=1"})},userlogout:function(){var e=this;this.$confirm("是否确认退出?","退出提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(()=>{Object(s["b"])().then(t=>{Object(i["b"])(),localStorage.removeItem("userInfo"),e.haslogin=!1,window.location.reload(),e.$message({type:"success",message:"退出成功!"}),this.$router.push({path:"/Home"})})}).catch(()=>{})},routeChange:function(){var e=this;e.pMenu=!0,this.activeIndex="/"==this.$route.path?"/Home":this.$route.path,localStorage.getItem("userInfo")?(e.haslogin=!0,e.userInfo=JSON.parse(localStorage.getItem("userInfo"))):e.haslogin=!1,this.getCategoryList(),"Share"!=this.$route.name&&"Home"!=this.$route.name||!this.$store.state.keywords?(this.input="",this.$store.state.keywords=""):this.input=this.$store.state.keywords}},components:{},watch:{$route:"routeChange"},created(){this.getUserAvatar();var e=this,t="hidden"in document?"hidden":"webkitHidden"in document?"webkitHidden":"mozHidden"in document?"mozHidden":null,n=t.replace(/hidden/i,"visibilitychange"),a=function(){document[t]||"/DetailShare"!=e.$route.path&&(localStorage.getItem("userInfo")?e.haslogin=!0:e.haslogin=!1)};document.addEventListener(n,a),this.routeChange()},mounted(){var e=this,t=setTimeout((function(){u(e.$store.state.themeObj.user_start,"#luke"),clearTimeout(t)}),1e3)}},h=l,d=(n("f269"),n("2877")),g=Object(d["a"])(h,a,r,!1,null,null,null);t["a"]=g.exports},f269:function(e,t,n){"use strict";n("607a")}}]);
//# sourceMappingURL=chunk-cc5c2816.5225376c.js.map