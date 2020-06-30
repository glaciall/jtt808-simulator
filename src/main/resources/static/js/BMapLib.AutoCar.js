
setTimeout(function()
{
    BMapLib.EventBinding.eventLoop();
    BMapLib.AutoMoving.start();
}, 100);

var BMapLib = window.BMapLib = BMapLib || {};
(function () {
    var b, a = b = a || {version: "1.5.0"};
    a.guid = "$BAIDU$";
    (function () {
        window[a.guid] = window[a.guid] || {};
        a.dom = a.dom || {};
        a.dom.g = function (e) {
            if ("string" == typeof e || e instanceof String) {
                return document.getElementById(e)
            } else {
                if (e && e.nodeName && (e.nodeType == 1 || e.nodeType == 9)) {
                    return e
                }
            }
            return null
        };
        a.g = a.G = a.dom.g;
        a.lang = a.lang || {};
        a.lang.isString = function (e) {
            return "[object String]" == Object.prototype.toString.call(e)
        };
        a.isString = a.lang.isString;
        a.dom._g = function (e) {
            if (a.lang.isString(e)) {
                return document.getElementById(e)
            }
            return e
        };
        a._g = a.dom._g;
        a.dom.getDocument = function (e) {
            e = a.dom.g(e);
            return e.nodeType == 9 ? e : e.ownerDocument || e.document
        };
        a.browser = a.browser || {};
        a.browser.ie = a.ie = /msie (\d+\.\d+)/i.test(navigator.userAgent) ? (document.documentMode || +RegExp["\x241"]) : undefined;
        a.dom.getComputedStyle = function (f, e) {
            f = a.dom._g(f);
            var h = a.dom.getDocument(f), g;
            if (h.defaultView && h.defaultView.getComputedStyle) {
                g = h.defaultView.getComputedStyle(f, null);
                if (g) {
                    return g[e] || g.getPropertyValue(e)
                }
            }
            return ""
        };
        a.dom._styleFixer = a.dom._styleFixer || {};
        a.dom._styleFilter = a.dom._styleFilter || [];
        a.dom._styleFilter.filter = function (f, j, k) {
            for (var e = 0, h = a.dom._styleFilter, g; g = h[e]; e++) {
                if (g = g[k]) {
                    j = g(f, j)
                }
            }
            return j
        };
        a.string = a.string || {};
        a.string.toCamelCase = function (e) {
            if (e.indexOf("-") < 0 && e.indexOf("_") < 0) {
                return e
            }
            return e.replace(/[-_][^-_]/g, function (f) {
                return f.charAt(1).toUpperCase()
            })
        };
        a.dom.getStyle = function (g, f) {
            var i = a.dom;
            g = i.g(g);
            f = a.string.toCamelCase(f);
            var h = g.style[f] || (g.currentStyle ? g.currentStyle[f] : "") || i.getComputedStyle(g, f);
            if (!h) {
                var e = i._styleFixer[f];
                if (e) {
                    h = e.get ? e.get(g) : a.dom.getStyle(g, e)
                }
            }
            if (e = i._styleFilter) {
                h = e.filter(f, h, "get")
            }
            return h
        };
        a.getStyle = a.dom.getStyle;
        a.dom._NAME_ATTRS = (function () {
            var e = {
                cellpadding: "cellPadding",
                cellspacing: "cellSpacing",
                colspan: "colSpan",
                rowspan: "rowSpan",
                valign: "vAlign",
                usemap: "useMap",
                frameborder: "frameBorder"
            };
            if (a.browser.ie < 8) {
                e["for"] = "htmlFor";
                e["class"] = "className"
            } else {
                e.htmlFor = "for";
                e.className = "class"
            }
            return e
        })();
        a.dom.setAttr = function (f, e, g) {
            f = a.dom.g(f);
            if ("style" == e) {
                f.style.cssText = g
            } else {
                e = a.dom._NAME_ATTRS[e] || e;
                f.setAttribute(e, g)
            }
            return f
        };
        a.setAttr = a.dom.setAttr;
        a.dom.setAttrs = function (g, e) {
            g = a.dom.g(g);
            for (var f in e) {
                a.dom.setAttr(g, f, e[f])
            }
            return g
        };
        a.setAttrs = a.dom.setAttrs;
        a.dom.create = function (g, e) {
            var h = document.createElement(g), f = e || {};
            return a.dom.setAttrs(h, f)
        };
        a.object = a.object || {};
        a.extend = a.object.extend = function (g, e) {
            for (var f in e) {
                if (e.hasOwnProperty(f)) {
                    g[f] = e[f]
                }
            }
            return g
        }
    })();
    BMapLib.Sequence = {
        sequence : 1,
        next : function()
        {
            return this.sequence++;
        }
    };
    BMapLib.EventBinding = {
        events : [],
        interval : null,
        clickListener : null,
        setClickListener : function(listener)
        {
            this.clickListener = listener;
        },
        eventLoop : function()
        {
            var self = this;
            $(document).on('click', '.BMap_Marker', function()
            {
                if (self.clickListener) self.clickListener(this.marker.getPosition(), this.data);
            });

            this.interval = setInterval(function()
            {
                for (var i = 0, l = self.events.length; i < l; i++)
                {
                    var event = self.events.shift();
                    if (!event) continue;
                    var el = self.getElement(event.marker);
                    if (!el) { self.events.push(event); continue; };
                    el.data = event.data;
                    el.marker = event.marker;
                }
            }, 200);
        },
        getElement : function(marker)
        {
            for (var j in marker)
            {
                var e = marker[j];
                if (e && e.tagName && e.className.indexOf('BMap_Marker') > -1)
                {
                    return e;
                }
            }
            return null;
        },
        bind : function(marker, data)
        {
            this.events.push({ marker : marker, data : data });
        }
    };
    BMapLib.AutoMoving = {
        movings : [],       // 正在执行运行的动作
        runningCars : {},   // 运行中的车辆
        movements : {},     // 待执行的移动运动
        _moveInterval : null,
        fragmentCount : 0,
        followingCar : null,
        loops : 0,
        start : function()
        {
            if (this._moveInterval) return;
            var self = this;
            this._moveInterval = setInterval(function() {
                self._move();
            }, 30);
        },
        add : function(action)
        {
            var key = 'car-' + action.car;
            var notExists = typeof(this.movements[key]) == 'undefined';

            if (this.runningCars[key])
            {
                this.movements[key].push(action);
            }
            else
            {
                if (notExists) this.movements[key] = [];
                this.runningCars[key] = true;
                this.movings.push(action);
            }
        },
        _move : function()
        {
            var fragmentCount = 0;
            var map = null;
            var time = new Date().getTime();
            var viewport = null;
            for (var i = 0, l = this.movings.length; i < l; i++)
            {
                var action = this.movings[i];
                // TODO: 如果单个循环耗时太长，可以通过id进行分片处理
                if (action == null)
                {
                    fragmentCount += 1;
                    continue;
                }

                if (null == map)
                {
                    map = action.marker == undefined ? null : action.marker.getMap();
                    if (map == null)
                    {
                        this.movings[i] = null;
                        continue;
                    }
                    viewport = map.getBounds();
                }

                action.idx++;
                var o = this._linear(action.curr.x, action.next.x, action.idx, action.steps);
                var r = this._linear(action.curr.y, action.next.y, action.idx, action.steps);
                var q = map.getMapType().getProjection().pointToLngLat(new BMap.Pixel(o, r));

                if (action.idx == 1)
                {
                    if(action.marker) action.marker.setRotation(action.angle);
                }
                if (action.autoView && !map.getBounds().containsPoint(q)) map.setCenter(q);

                if (viewport.containsPoint(q))
                {
                    var meters = Math.sqrt(Math.pow(action.y - r, 2) + Math.pow(action.x - o, 2));
                    // 为了得到更平滑的动画移动效果，先屏蔽这个判断，如果要同屏显示大量图标可取消注释
                    // if (meters > 2)
                    // {
                        if(action.marker) action.marker.setPosition(q);
                        action.x = o;
                        action.y = r;
                    // }
                }

                if (action.idx >= action.steps)
                {
                    var key = 'car-' + action.car;
                    var a = this.movements[key].shift();
                    this.movings[i] = a ? a : null;
                    if (!a) this.runningCars[key] = false;
                }
            }
            time = new Date().getTime() - time;
            if ((this.loops++) % 100 == 99) console.log('[AutoMoving], length: %d, fragments: %d, spend: %d ms', this.movings.length, fragmentCount, time);

            // 当碎片数量超过N时，进行一次清理
            if (fragmentCount < 1000) return;
            var list = [];
            for (var i = 0; i < this.movings.length; i++)
            {
                if (this.movings[i]) list.push(this.movings[i]);
            }
            this.movings = list;
        },
        _linear : function (f, j, h, i) {
            var e = f, l = j - f, g = h, k = i;
            return l * g / k + e;
        }
    };
    // @g map实例
    // @p 初始位置
    // @e 参数项：{ label : '', labelColor : '', icon : null, enableRotation : true }
    var c = BMapLib.AutoCar = function (g, p, e)
    {
        this._id = BMapLib.Sequence.next();
        this._map = g;
        this._path = [p];
        this._angle = 0;
        this.i = 0;
        this.visibility = true;
        this._setTimeoutQuene = [];
        this._projection = this._map.getMapType().getProjection();
        this._opts = {icon: null, speed: 4000, defaultContent: "", landmarkPois : [], rotation : "0"};
        this._setOptions(e);
        p.arrivalTime = e.time;
        if (!e.iconOffset) this._opts.iconOffset = new BMap.Size(0 - this._opts.icon.size.width / 2, 0 - this._opts.icon.size.height / 2);
        this._rotation = 0;
        if (!this._opts.icon instanceof BMap.Icon) {
            this._opts.icon = defaultIcon
        }
    };
    c.prototype._setOptions = function (e) {
        if (!e) {
            return
        }
        for (var f in e) {
            if (e.hasOwnProperty(f)) {
                this._opts[f] = e[f]
            }
        }
    };
    c.prototype.show = function()
    {
        this._addMarker();
    };
    c.prototype.setVisibility = function(visible)
    {
        if (!this._marker) this.show();
        if (visible) this._marker.show();
        else this._marker.hide();

        this._marker._visible = visible;

        this.visibility = visible;
    };
    c.prototype.getVisiblity = function()
    {
        return this.visibility;
    };
    // p: 目标位置，speed：移动速度，米/秒
    c.prototype.moveTo = function(p, arrivalTime)
    {
        p.arrivalTime = arrivalTime;
        this._path.push(p);
        this._moveNext();
    };
    c.prototype.remove = function()
    {
        if (this._overlay) this._map.removeOverlay(this._overlay);
        if (this._marker) this._map.removeOverlay(this._marker);
    };
    c.prototype.setLabel = function(text, color)
    {
        this._opts.label = text;
        if (color) this._opts.labelColor = color;
        if (!this._label)
        {
            var label = new BMap.Label(this._opts.label);
            label.setStyles({
                'z-index': 1000000,
                'border': 'none',
                'background-color': this._opts.labelColor || '#00537f',
                'color': '#efefef',
                'padding': '2px 0px',
                'text-align':'center',
                'box-sizing':'border-box'
            });
            this._label = label;
            this._marker.setLabel(label);
        }
        this._label.setContent(text);
        if (color) this._label.setStyles({ backgroundColor : color });
    };
    c.prototype.setData = function(data)
    {
        this._opts.data = data;
    };
    c.prototype.getData = function()
    {
        return this._opts.data;
    };
    c.prototype.getMarker = function()
    {
        return this._marker;
    };
    c.prototype.getOverlay = function()
    {
        return this.getMarker();
    };
    c.prototype.getPosition = function()
    {
        return this._marker.getPosition();
    };
    c.prototype.setIcon = function(icon)
    {
        if (!icon) return;
        if (icon.imageUrl == this._opts.icon.imageUrl) return;
        this._opts.icon = icon;
        if (this._marker)
        {
            this._marker.setIcon(icon);
            this._marker.setRotation(this._angle);
        }
    };
    c.prototype.setAutoView = function(isAutoView)
    {
        if (BMapLib.AutoMoving.followingCar) BMapLib.AutoMoving.followingCar._opts.autoView = false;
        BMapLib.AutoMoving.followingCar = this;
        this._opts.autoView = isAutoView;
    };
    a.object.extend(c.prototype, {
        _addMarker: function (f) {
            if (this._marker) {
                // this.stop();
                // this._map.removeOverlay(this._marker);
                // clearTimeout(this._timeoutFlag);
                return;
            }
            this._overlay && this._map.removeOverlay(this._overlay);
            var e = new BMap.Marker(this._path[0], { icon : this._opts.icon, offset : this._opts.iconOffset, rotation :this._opts.rotation});
            this._map.addOverlay(e);
            BMapLib.EventBinding.bind(e, this._opts.data);
            if (this._opts.label)
            {
                var label = new BMap.Label(this._opts.label);
                label.setStyles({
                    'z-index': 1000000,
                    'border': 'none',
                    'background-color': this._opts.labelColor,
                    'color': '#efefef',
                    'padding': '2px 10px',
                    'width' : this._opts.icon.size.width + 'px',
                    'max-width' : this._opts.icon.size.width + 'px',
                    'margin-top' : '-20px',
                });
                e.setLabel(label);
                this._label = label;
            }
            e._visible = true;
            this._marker = e;
        },
        _getMercator: function (e) {
            return this._map.getMapType().getProjection().lngLatToPoint(e)
        },
        _getDistance: function (f, e) {
            return Math.sqrt(Math.pow(f.x - e.x, 2) + Math.pow(f.y - e.y, 2))
        },
        _move: function (n, j)
        {
            var i = this;
            var h = 0, e = 30;
            var l = this._projection.lngLatToPoint(n), k = this._projection.lngLatToPoint(j);
            var distance = i._getDistance(l, k);
            var speed = distance * 1000 / (j.arrivalTime - n.arrivalTime);
            var f = speed / (1000 / e);
            var g = Math.round(distance / f);
            if (g < 1 || isNaN(g)) return;

            var action = {
                car : i._id,
                idx : 0,
                steps : g,
                curr : l,
                x : l.x,
                y : l.y,
                next : k,
                angle : i.getAngle(l, k),
                marker : i._marker,
                autoView: i._opts.autoView
            };
            BMapLib.AutoMoving.add(action);
        },
        getAngle : function(f, m)
        {
            var j = this;
            var e = 0;
            if (m.x != f.x) {
                var k = (m.y - f.y) / (m.x - f.x), g = Math.atan(k);
                e = g * 360 / (2 * Math.PI);
                if (m.x > f.x)
                {
                    e = e + 180;
                }

                return j._angle = - e;
            } else {
                var h = m.y - f.y;
                var i = 0;
                if (h > 0) {
                    i = -1;
                } else {
                    i = 1;
                }
                return j._angle = i * 90 + 180;
            }
        },
        setRotation: function (l, f, m) {
            var j = this;
            var e = 0;
            f = j._map.pointToPixel(f);
            m = j._map.pointToPixel(m);
            if (m.x != f.x) {
                var k = (m.y - f.y) / (m.x - f.x), g = Math.atan(k);
                e = g * 360 / (2 * Math.PI);
                if (m.x < f.x) {
                    e = -e + 90 + 90
                } else {
                    e = -e
                }
                j._marker.setRotation(j._angle = -e + 180);
            } else {
                var h = m.y - f.y;
                var i = 0;
                if (h > 0) {
                    i = -1
                } else {
                    i = 1
                }
                j._marker.setRotation(j._angle = -i * 90 + 180);
            }
            return
        },
        linePixellength: function (f, e) {
            return Math.sqrt(Math.abs(f.x - e.x) * Math.abs(f.x - e.x) + Math.abs(f.y - e.y) * Math.abs(f.y - e.y))
        },
        pointToPoint: function (f, e) {
            return Math.abs(f.x - e.x) * Math.abs(f.x - e.x) + Math.abs(f.y - e.y) * Math.abs(f.y - e.y)
        },
        _moveNext: function() {
            var f = this;
            if (this._path.length > 1)
            {
                f._move(f._path.shift(), f._path[0]);
            }
        },
        _pauseForView: function (e) {
            var g = this;
            var f = setTimeout(function () {
                g._moveNext(++g.i)
            }, g._opts.landmarkPois[e].pauseTime * 1000);
            g._setTimeoutQuene.push(f)
        },
        _clearTimeout: function () {
            for (var e in this._setTimeoutQuene) {
                clearTimeout(this._setTimeoutQuene[e])
            }
            this._setTimeoutQuene.length = 0
        },
        _tween: {
            linear: function (f, j, h, i) {
                var e = f, l = j - f, g = h, k = i;
                return l * g / k + e
            }
        },
        _troughPointIndex: function (f) {
            var h = this._opts.landmarkPois, j;
            for (var g = 0, e = h.length; g < e; g++) {
                if (!h[g].bShow) {
                    j = this._map.getDistance(new BMap.Point(h[g].lng, h[g].lat), f);
                    if (j < 10) {
                        h[g].bShow = true;
                        return g
                    }
                }
            }
            return -1
        }
    });
})();