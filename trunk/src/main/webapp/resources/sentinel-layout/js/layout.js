var Sentinel = {
    
    init: function() {
        this.menubar = $('#layout-menubar');
        this.content = $('#layout-portlets-cover');
        this.initTopMenu();
        this.activeMenubarsArr = [];        
        this.restoreSentinelMenuState();
        this.menu_state = 'open';
        this.headMenuState = 'close';
        this.leftMenuState = 'close';
        this.bindEvents();
        Sentinel.WindowResized();
    },
            
    initTopMenu: function() {
        var $this = this;
        this.topMenu = $('#sm-topmenu');
        this.topMenuItems = this.topMenu.children('li');

        this.topMenuItems.on('mouseenter', function() {
            if($(window).width() < 640) {
                return;
            }
            
            if($this.topMenuHideTimeout) {
                clearTimeout($this.topMenuHideTimeout);
            }

            var item = $(this);
            if($this.activeTopMenuItem && $this.activeTopMenuItem.get(0) !== item.get(0)) {
                $this.hideTopMenuItem($this.activeTopMenuItem);
            }

            $this.activeTopMenuItem = item;
            item.children('.layout-header-widgets-submenu').show();
        })
        .on('mouseleave', function() {
            if($(window).width() < 640) {
                return;
            }
            
            var item = $(this);
            $this.topMenuHideTimeout = setTimeout(function() {
                $this.hideTopMenuItem(item);
            }, 1000);
        })
        .on('click', function() {
            if($(window).width() < 640) {
                $(this).height('auto').siblings().height(47).children('ul').hide();
                $(this).children('ul').show();
            }
            else {
                $(this).children('ul').toggle();
            }
        });
    },
    
    hideTopMenuItem: function(item) {
        item.children('.layout-header-widgets-submenu').hide();
    },
            
    toggleLeftMenu: function() {
        var winW = $(window).width();

        if(this.menu_state === 'open') {
            this.content.width(winW - 50);
            this.menubar.addClass('slimmenu').removeClass('bigmenu');
            $('#searchArea').addClass('slimsearch');
            this.menu_state = 'close';
        } 
        else {
            this.content.width(winW - 261);
            this.menubar.removeClass('slimmenu').addClass('bigmenu');
            $('#searchArea').removeClass('slimsearch');
            this.menu_state = 'open';
        }
    },
    
    bindEvents: function() {
        var win = $(window),
        $this = this;

        win.on("resize.sentinel load.sentinel", function() {
            Sentinel.WindowResized();
        });
        
        //menubar resize btn binding
        $('#layout-menubar-resize').on('click', function() {
            Sentinel.toggleLeftMenu();
        });

        // responsive mode menubar open button
        $('#layout-menubar-resize2').on('click', function() {
            if($this.leftMenuState === 'close') {
                $this.menubar.addClass('layout-menubar-open-fullscr').removeClass('slimmenu');
                $('body').addClass('OvHidden');
                $this.leftMenuState = 'open';
            } 
            else {
                $this.menubar.removeClass('layout-menubar-open-fullscr');
                $this.leftMenuState = 'close';
                $('body').removeClass('OvHidden');
            }

        });

        // responsive mode header bar open menus binding
        $('#ResponsiveModeOneMenu').on('click', function() {
            if($this.headMenuState == 'close') {
                $('.MustResponsive').addClass('layout-header-widgets-mobile');
                $this.headMenuState = 'open';
                $('body').addClass('OvHidden');
            }
            else {
                $('.MustResponsive').removeClass('layout-header-widgets-mobile');
                $this.headMenuState = 'close';
                $('body').removeClass('OvHidden');
            }

        });

        $('.TabBtn').bind('click', function() {
            $('.TabBtn').removeClass('TabBtnActiveLeft').removeClass('TabBtnActiveRight');
            $('.TabContent').addClass('DispNone');

            if($(this).hasClass('left')) {
                $(this).addClass('TabBtnActiveLeft');
                $('#TAB' + $(this).attr('role')).removeClass('DispNone');
            } else {
                $(this).addClass('TabBtnActiveRight');
                $('#TAB' + $(this).attr('role')).removeClass('DispNone');
            }
        });
    },
    
    WindowResized: function() {
        var $this = this,
        win = $(window),
        winW = win.width(),
        winH = win.height(),
        menuWid = this.menubar.width();

        this.content.width(winW - (menuWid + 1));
        this.menubar.css({'min-height': winH});
        this.content.css('min-height', winH + 'px');

        if(winW > 640) {
            $('#sm-topmenu > li').height('34px');
            this.menubar.removeClass('ThisIsSmallMenu layout-menubar-open-fullscr').addClass('bigmenu');
            $('body').removeClass('OvHidden');
            $this.leftMenuState = 'close';
            $this.menu_state = 'open';
        }
        else if(winW < 640) {
            $('#sm-topmenu > li').height('47px');
            this.menubar.addClass('ThisIsSmallMenu');
            this.content.width('100%');
        }

        if(winW > 640 && winW < 1200) {
            this.menubar.addClass('slimmenu');
            $this.menu_state = 'close';
            this.content.width(winW - (menuWid + 1));
            $('#searchArea').addClass('slimsearch');
            $('#buttonArea').hide();
        } else if(winW < 640 || winW > 1200) {
            this.menubar.removeClass('slimmenu');
            $this.menu_state = 'open';
            $('#searchArea').removeClass('slimsearch');
            $('#buttonArea').show();
        }

    },
            
    openSubMenu: function(whichBtn) {
        var btn = $(whichBtn),
                $this = this,
                parentOfBtn = btn.closest('li'),
                subMenuContainer = parentOfBtn.children('ul.layout-menubar-submenu-container'),
                activeMenubars = $.cookie('sentinel_menustate');

        if(activeMenubars) {
            $this.activeMenubarsArr = activeMenubars.split(',');
        }

        if(subMenuContainer.length && subMenuContainer.css('display') !== 'none') {            
            subMenuContainer.css('display', 'none');
            var parentMenu = parentOfBtn.closest('ul').closest('li');
            if(parentMenu) {
                parentOfBtn.removeClass('layout-menubar-active');
                parentMenu.addClass('layout-menubar-active');
                $this.activeMenubarsArr = $.grep($this.activeMenubarsArr, function(value) {
                    return value !== parentMenu.attr('id');
                });
                $this.activeMenubarsArr.push(parentMenu.attr('id'));
            }
            else {
                parentOfBtn.addClass('layout-menubar-active');
            }
            $this.refreshCookie(parentOfBtn.attr('id'));
        }
        else {
            if($(whichBtn).parent().hasClass('firstnode')) {
                $.removeCookie('sentinel_menustate', {path: '/'});
                $this.activeMenubarsArr = [];
                $('.firstnode ul').slideUp(300);
                $(".layout-menubar-active").removeClass('layout-menubar-active');
                parentOfBtn.addClass("layout-menubar-active");

                if(subMenuContainer.length) {
                    subMenuContainer.each(function() {
                        $(this).slideDown(300);
                        $this.activeMenubarsArr.push(parentOfBtn.attr('id'));
                        $.cookie('sentinel_menustate', $this.activeMenubarsArr, {path: '/'});
                        return false;
                    });
                } else {
                    $this.activeMenubarsArr.push(parentOfBtn.attr('id'));
                    $.cookie('sentinel_menustate', $this.activeMenubarsArr, {path: '/'});
                }
            } else {
                $(".layout-menubar-active").removeClass('layout-menubar-active');
                parentOfBtn.addClass("layout-menubar-active");
                subMenuContainer.each(function() {
                    $(this).slideDown(300);
                    $this.activeMenubarsArr.push(parentOfBtn.attr('id'));
                    $.cookie('sentinel_menustate', $this.activeMenubarsArr, {path: '/'});
                    return false;
                });
            }
        }

    },

    toggleCodes: function(on) {
        var obj = document.getElementById('icons');

        if (on) {
            obj.className += ' codesOn';
        } else {
            obj.className = obj.className.replace(' codesOn', '');
        }
    },
            
    restoreSentinelMenuState: function() {
        $(".layout-menubar-submenu-container").hide();
        var activeMenubars = $.cookie('sentinel_menustate');

        if(activeMenubars) {
            var activeIndexes = activeMenubars.split(',');
            $(".layout-menubar-active").removeClass('layout-menubar-active');

            for(var i = 0; i < activeIndexes.length; i++) {
                var activeIndex = activeIndexes[i],
                        menubar = document.getElementById(activeIndex);

                if(i === (activeIndexes.length - 1)) {
                    $(menubar).addClass('layout-menubar-active');
                }
                $(menubar).children('ul').css("display", "block");
            }
        }
    },
            
    refreshCookie: function(id) {
        $.removeCookie('sentinel_menustate', {path: '/'});
        this.activeMenubarsArr = $.grep(this.activeMenubarsArr, function(value) {
            if (value) {
                var menubar = document.getElementById(value);
                if(value.indexOf(id) === -1) {
                    return true;
                }
                else {
                    $(menubar).children('ul').css('display', 'none');
                    return false;
                }
            }
            else {
                return false;
            }
        });
        $.cookie('sentinel_menustate', this.activeMenubarsArr, {path: '/'});
    }
};

$(function() {
    Sentinel.init();
});

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2013 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// CommonJS
		factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write

		if (value !== undefined && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setTime(+t + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {};

		// To prevent the for loop in the first place assign an empty array
		// in case there are no cookies at all. Also prevents odd result when
		// calling $.cookie().
		var cookies = document.cookie ? document.cookie.split('; ') : [];

		for (var i = 0, l = cookies.length; i < l; i++) {
			var parts = cookies[i].split('=');
			var name = decode(parts.shift());
			var cookie = parts.join('=');

			if (key && key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		if ($.cookie(key) === undefined) {
			return false;
		}

		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};

}));