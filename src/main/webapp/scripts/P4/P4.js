goog.provide('P4');

goog.require('lime.Director');
goog.require('lime.Scene');
goog.require('lime.Layer');
goog.require('lime.Circle');
goog.require('lime.animation.MoveBy');
goog.require('lime.RoundedRect');

var director, scene, layer;

P4.start = function() {
	director = new lime.Director(document.body, 400, 600);
	scene = new lime.Scene();
	layer = new lime.Layer().setPosition(0, 0);

	var background = new lime.Layer();
	background.appendChild(new lime.RoundedRect().setSize(700, 600).setFill(0, 0, 255));
	for ( var i = 0; i < 7; i++) {
		for ( var j = 0; j < 6; j++) {
			background.appendChild(new lime.Circle().setSize(50, 50)
					.setPosition(50* i + 25, 50* j + 25).setFill(255, 255, 255));
		}
	}

	scene.appendChild(background);
	scene.appendChild(layer);
	director.replaceScene(scene);
};

P4.addChip = function(column, height, player) {
	var newChip = new lime.Circle().setSize(50, 50).setPosition(25 + column*50, 25).setFill(255, 0, 0);
	layer.appendChild(newChip);
	
	var fallDown = new lime.animation.MoveBy(0, 250 - height*50).setDuration(3 - height*0.5);
	newChip.runAction(fallDown);
};

// this is required for outside access after code is compiled in
// ADVANCED_COMPILATIONS mode
goog.exportSymbol('P4.start', P4.start);
goog.exportSymbol('P4.addChip', P4.addChip);
