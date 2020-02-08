/* handle the sliding panel */

function slide_panel_toggle(widget) {
    let anim_time = 300;
    let anim_mode = "swing";

    if (widget.hasClass("slide-up")) {
        widget.addClass("slide-down", anim_time, anim_mode);
        widget.removeClass("slide-up");
    } else {
        widget.removeClass("slide-down");
        widget.addClass("slide-up", anim_time, anim_mode);
    }
}

function slide_panel_is_up(widget) { return widget.hasClass("slide-up"); }

function change_slide_panel_context(widget, ctx, newctx) {
    if (ctx === newctx) {
        if (slide_panel_is_up(widget))
            slide_panel_toggle(widget);
        $(ctx).hide();
        return;
    }

    if (ctx !== null)
        $(ctx).hide();

    $(newctx).show();
    if (!slide_panel_is_up(widget))
        slide_panel_toggle(widget);

    return newctx;
}

$(document).ready(function () {
    let slup = $("#slide-up-menu");
    let filter_btn = $("#filter-button");
    let list_btn = $("#list-button");
    let search_btn = $("#search-button");
    let ctx = "";

    filter_btn.on("click", function() {
        ctx = change_slide_panel_context(slup, ctx, "#filter-page");
    });

    list_btn.on("click", function() {
        ctx = change_slide_panel_context(slup, ctx, "#list-page");
    });

    search_btn.on("click", function() {
        ctx = change_slide_panel_context(slup, ctx, "#search-page");
    });
});
